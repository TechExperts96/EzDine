package com.techexpert.ezdine.app.main.detail

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.techexpert.ezdine.app.R
import com.techexpert.ezdine.app.databinding.FragmentItemDetailsBinding
import com.techexpert.ezdine.app.foundation.base.BaseFragment
import com.techexpert.ezdine.app.foundation.base.UIEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailFragment :
    BaseFragment<FragmentItemDetailsBinding, ItemDetailViewModel>(
        R.layout.fragment_item_details,
        ItemDetailViewModel::class
    ) {

    override fun onUIEventTriggered(event: UIEvent) {
        when (event) {
            is UIEvent.ShowLoadingDialog -> {
                showProgressDialog()
            }
            is UIEvent.OnNextClick -> {
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.onBackPressedDispatcher?.addCallback(
            /** here enable flag works like below
             true : will not call the super OnBackPressedCallback
             false : will call the super OnBackPressedCallback*/
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )
    }
}
