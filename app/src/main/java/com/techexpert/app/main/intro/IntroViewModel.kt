package com.techexpert.app.main.intro

import com.techexpert.app.foundation.base.BaseViewModel
import com.techexpert.app.foundation.base.UIEvent
import com.techexpert.app.foundation.network.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val baseRepository: BaseRepository
) :
    BaseViewModel() {

    fun onSkipClick() {
        publishUIEvent(UIEvent.OnSkipClick)
    }

    fun onNextClick() {
        publishUIEvent(UIEvent.OnNextClick)
    }
}
