package com.techexpert.app.main.intro

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.techexpert.app.R
import com.techexpert.app.databinding.FragmentIntroBinding
import com.techexpert.app.foundation.base.BaseFragment
import com.techexpert.app.foundation.base.UIEvent
import com.techexpert.app.foundation.network.models.response.app.CarouselModel
import com.techexpert.app.main.IntroPagerAdapter
import com.techexpert.app.main.MainActivity
import com.techexpert.app.util.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment :
    BaseFragment<FragmentIntroBinding, IntroViewModel>(
        R.layout.fragment_intro,
        IntroViewModel::class
    ) {

    lateinit var adapter: PagerAdapter

    override fun FragmentIntroBinding.initialize() {
        activity?.onBackPressedDispatcher?.addCallback(
            /** here enable flag works like below
             true : will not call the super OnBackPressedCallback
             false : will call the super OnBackPressedCallback*/
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (requireActivity() as MainActivity).finish()
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCarousels()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onUIEventTriggered(event: UIEvent) {
        when (event) {
            is UIEvent.APIErrorResponse -> {
                requireContext().showShortToast(event.error)
            }
            is UIEvent.ShowLoadingDialog -> {
                showProgressDialog()
            }
            is UIEvent.RenderCarousel -> {
                setupIntroPagerAdapter(event.carousels)
                hideProgressDialog()
            }
            is UIEvent.OnNextClick -> {
                if (binding.introPager.currentItem == adapter.count - 1) {
                    // TODO: navigate to next screen
                } else {
                    binding.introPager.setCurrentItem(binding.introPager.currentItem + 1, true)
                }
            }
            is UIEvent.OnSkipClick -> {
                binding.introPager.setCurrentItem(adapter.count - 1, true)
            }
        }
    }

    private fun setupIntroPagerAdapter(carousels: ArrayList<CarouselModel>) {
        adapter = IntroPagerAdapter(requireContext(), carousels, viewModel)
        binding.introPager.adapter = adapter
        binding.intoTabLayout.setupWithViewPager(binding.introPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
