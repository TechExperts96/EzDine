package com.techexpert.app.foundation.base

import com.techexpert.app.foundation.network.models.response.app.CarouselModel

abstract class UIEvent {

    object Empty : UIEvent()

    object ShowLoadingDialog : UIEvent()

    object DismissLoadingDialog : UIEvent()
    object OnNextClick : UIEvent()
    object OnSkipClick : UIEvent()

    data class APIErrorResponse(var error: String) : UIEvent()

    data class RenderCarousel(
        var carousels: ArrayList<CarouselModel>
    ) : UIEvent()
}
