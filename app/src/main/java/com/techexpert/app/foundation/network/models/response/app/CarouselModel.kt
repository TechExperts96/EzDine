package com.techexpert.app.foundation.network.models.response.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarouselModel(
    val title: String,
    val description: String,
    val imageUrlMedium: String
) : Parcelable
