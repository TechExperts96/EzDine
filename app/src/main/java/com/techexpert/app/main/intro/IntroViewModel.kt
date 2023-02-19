package com.techexpert.app.main.intro

import androidx.lifecycle.viewModelScope
import com.techexpert.app.foundation.base.BaseViewModel
import com.techexpert.app.foundation.base.UIEvent
import com.techexpert.app.foundation.network.models.response.CarouselRes
import com.techexpert.app.foundation.network.models.response.app.CarouselModel
import com.techexpert.app.foundation.network.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val baseRepository: BaseRepository
) :
    BaseViewModel() {
    fun getCarousels() {
        viewModelScope.launch {
            publishUIEvent(UIEvent.ShowLoadingDialog)
            baseRepository.getCarousels("*")
                .onSuccess {
                    publishUIEvent(UIEvent.RenderCarousel(convertToCarouselModel(it)))
                }
                .onFailure {
                    failureHandling(it)
                }
        }
    }

    fun onSkipClick() {
        publishUIEvent(UIEvent.OnSkipClick)
    }

    fun onNextClick() {
        publishUIEvent(UIEvent.OnNextClick)
    }

    private fun convertToCarouselModel(it: CarouselRes): ArrayList<CarouselModel> {
        val carouselModels = ArrayList<CarouselModel>()
        for (i in it.data) {
            val carouselModel = CarouselModel(
                i.attributes.title,
                i.attributes.description,
                i.attributes.visual.data.attributes.formats.medium.url
            )
            carouselModels.add(carouselModel)
        }
        return carouselModels
    }
}
