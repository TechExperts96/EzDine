package com.techexpert.app.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.techexpert.app.R
import com.techexpert.app.databinding.AdapterIntroPagerSliderBinding
import com.techexpert.app.foundation.network.models.response.app.CarouselModel
import com.techexpert.app.main.intro.IntroViewModel
import com.techexpert.app.util.gone
import com.techexpert.app.util.visible

internal class IntroPagerAdapter(
    context: Context,
    arrayList: ArrayList<CarouselModel>,
    viewModel: IntroViewModel
) : PagerAdapter() {

    private val context: Context
    private val carouselModels: List<CarouselModel>
    private val layoutInflater: LayoutInflater
    private val viewModel: IntroViewModel

    init {
        this.context = context
        this.carouselModels = arrayList
        this.viewModel = viewModel
        layoutInflater =
            this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return carouselModels.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = DataBindingUtil.inflate<AdapterIntroPagerSliderBinding>(
            layoutInflater,
            R.layout.adapter_intro_pager_slider,
            container,
            false
        )
        binding.vm = viewModel
        binding.carousel = carouselModels[position]
        container.addView(binding.root)
        if (position == carouselModels.size - 1) {
            binding.btnNext.text = container.context.getText(R.string.btn_get_started)
            binding.btnSkip.gone()
        } else {
            binding.btnNext.text = container.context.getText(R.string.btn_next)
            binding.btnSkip.visible()
        }
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
