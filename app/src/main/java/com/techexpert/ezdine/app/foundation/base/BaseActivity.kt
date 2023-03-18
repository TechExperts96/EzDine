package com.techexpert.ezdine.app.foundation.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.techexpert.ezdine.app.BR
import com.techexpert.ezdine.app.R
import com.techexpert.ezdine.app.util.hideKeyboard
import com.techexpert.ezdine.app.util.showShortToast
import kotlin.reflect.KClass

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes var layoutId: Int,
    viewModelClass: KClass<VM>
) :
    AppCompatActivity() {

    private var _viewModelClass = viewModelClass

    private var _binding: B? = null

    val binding: B get() = _binding!!

    // Make it open, so it can be overridden in child
    open fun B.initialize() {}

    lateinit var viewModel: VM

    private lateinit var loadingProgressBar: Dialog
    private var progressBar: ProgressBar? = null
    abstract fun onUIEventTriggered(event: UIEvent)
    private var isActivityPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[_viewModelClass.java]
        _binding = DataBindingUtil.setContentView(this, layoutId)
        lifecycle.addObserver(viewModel)

        binding.lifecycleOwner = this
        binding.setVariable(BR.lifecycle, this)

        binding.setVariable(BR.vm, viewModel)
        loadingProgressBar = generateLoadingDialog()
        subscribeUIEvent()
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        hideProgressDialog()
        binding.unbind()
        _binding?.unbind()
        super.onDestroy()
    }

    public fun showProgressDialog() {
        if (loadingProgressBar.isShowing) {
            return
        }
        loadingProgressBar.show()
    }

    fun hideProgressDialog() {
        if (!loadingProgressBar.isShowing) {
            return
        }

        loadingProgressBar.dismiss()
    }

    private fun generateLoadingDialog(): Dialog {
        loadingProgressBar = Dialog(this)
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

    private fun subscribeUIEvent() {
        viewModel.uiState.observe(this) { event: Any? ->
            if (event is UIEvent && !isActivityPaused) {
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
                        showShortToast(event.error)
                    }
                    else -> {
                        onUIEventTriggered(event)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isActivityPaused = true
    }

    override fun onResume() {
        super.onResume()
        isActivityPaused = false
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    /**  Button Single Click (Start) **/
    fun buttonOneClick(v: View, delayMillis: Long = 800, showKeyboard: Boolean = true) {
        if (delayMillis > 0) {
            v.isClickable = false
            v.postDelayed({ v.isClickable = true }, delayMillis)
        }
        if (showKeyboard) {
            v.context.hideKeyboard(v)
        }
    }
}
