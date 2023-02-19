package com.techexpert.app.foundation.base

abstract class UIEvent {

    object Empty : UIEvent()

    object ShowLoadingDialog : UIEvent()

    object DismissLoadingDialog : UIEvent()
    object OnNextClick : UIEvent()
    object OnSkipClick : UIEvent()

    data class APIErrorResponse(var error: String) : UIEvent()
}
