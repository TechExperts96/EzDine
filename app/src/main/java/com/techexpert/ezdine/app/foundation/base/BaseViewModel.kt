package com.techexpert.ezdine.app.foundation.base

import androidx.lifecycle.*
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val _uiState = MutableLiveData<UIEvent>(UIEvent.Empty)
    val uiState: LiveData<UIEvent> = _uiState

    internal fun <T : UIEvent> publishUIEvent(event: T) {
        viewModelScope.launch {
            _uiState.value = event
        }
    }

    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { postValue(initialValue) }

    fun failureHandling(t: Throwable) {
        var errorMessage = ""
        t.message?.let { errorMessage = it }

        publishUIEvent(UIEvent.DismissLoadingDialog)
        publishUIEvent(UIEvent.APIErrorResponse(errorMessage))
    }
}
