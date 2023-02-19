package com.techexpert.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App @Inject constructor() : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
