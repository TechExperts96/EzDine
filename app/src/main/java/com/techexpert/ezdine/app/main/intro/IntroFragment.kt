package com.techexpert.ezdine.app.main.intro

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.techexpert.ezdine.app.R
import com.techexpert.ezdine.app.databinding.FragmentIntroBinding
import com.techexpert.ezdine.app.foundation.base.BaseFragment
import com.techexpert.ezdine.app.foundation.base.UIEvent
import com.techexpert.ezdine.app.main.MainActivity
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
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.dark_black)
        var foodCategoryList = ArrayList<String>()
        foodCategoryList.add("Chinese")
        foodCategoryList.add("Main course")
        foodCategoryList.add("Break fast")
        foodCategoryList.add("Dessert")
//        foodCategoryList.forEach { category ->
//            binding.cgFoodCategories.addChip(requireActivity(), category)
//        }
    }

    private fun ChipGroup.addChip(context: Context, label: String) {
        try {
            var chip = layoutInflater.inflate(R.layout.row_chip_view, this, false) as Chip
            chip.apply {
                id = View.generateViewId()
                text = label
                isClickable = true
                isCheckable = true
                setChipSpacingHorizontalResource(R.dimen.dm_16)
                isCheckedIconVisible = false
                isFocusable = true
                addView(this)
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
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
            is UIEvent.ShowLoadingDialog -> {
                showProgressDialog()
            }
            is UIEvent.OnNextClick -> {
            }
            is UIEvent.OnSkipClick -> {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
