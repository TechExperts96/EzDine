package com.techexpert.ezdine.app.foundation.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.techexpert.ezdine.app.BR
import com.techexpert.ezdine.app.R
import com.techexpert.ezdine.app.util.showShortToast
import kotlin.reflect.KClass

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes val layoutId: Int,
    viewModelClass: KClass<VM>
) : Fragment() {

    private var _viewModelClass = viewModelClass

    lateinit var viewModel: VM

    private var _binding: B? = null

    val binding: B get() = _binding!!

    // Make it open, so it can be overridden in child fragments
    open fun B.initialize() {}

    private lateinit var loadingProgressBar: Dialog
    abstract fun onUIEventTriggered(event: UIEvent)
    var isFragmentPaused = false
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[_viewModelClass.java]
        lifecycle.addObserver(viewModel)
        loadingProgressBar = generateLoadingDialog()
        subscribeUIEvent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.lifecycle, viewLifecycleOwner)
        binding.setVariable(BR.vm, viewModel)
        binding.initialize()
        return binding.root
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        hideProgressDialog()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showProgressDialog() {
        if (loadingProgressBar.isShowing) {
            return
        }
        loadingProgressBar.show()
    }

    protected fun hideProgressDialog() {
        if (!loadingProgressBar.isShowing) {
            return
        }

        loadingProgressBar.dismiss()
        loadingProgressBar.cancel()
    }

    private fun generateLoadingDialog(): Dialog {
        loadingProgressBar = Dialog(requireContext())
        loadingProgressBar.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingProgressBar.setContentView(R.layout.popup_progressbar)
        loadingProgressBar.setCancelable(false)
        loadingProgressBar.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )

        progressBar = loadingProgressBar.findViewById<View>(R.id.progressBar1) as ProgressBar
        progressBar!!.indeterminateDrawable.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                Color.RED,
                BlendModeCompat.SRC_ATOP
            )

        return loadingProgressBar
    }

    override fun onPause() {
        super.onPause()
        hideProgressDialog()
        isFragmentPaused = true
    }

    override fun onResume() {
        super.onResume()
        isFragmentPaused = false
    }

    private fun subscribeUIEvent() {
        viewModel.uiState.observe(this) { event: Any? ->
            if (event is UIEvent && !isFragmentPaused) {
                when (event) {
                    is UIEvent.Empty -> {}
                    is UIEvent.ShowLoadingDialog -> {
                        showProgressDialog()
                    }
                    is UIEvent.DismissLoadingDialog -> {
                        hideProgressDialog()
                    }
                    is UIEvent.APIErrorResponse -> {
                        // Change UI here
                        requireContext().showShortToast(event.error)
                    }
                    else -> {
                        onUIEventTriggered(event)
                    }
                }
            }
        }
    }
}
