package com.techexpert.app.foundation.network.interfaces

import com.techexpert.app.foundation.network.HttpConstants
import com.techexpert.app.foundation.network.models.response.CarouselRes
import retrofit2.http.GET
import retrofit2.http.Query

interface RBService {
    @GET(HttpConstants.GET_SPLASH)
    suspend fun getSplash(): Result<Any>

    @GET(HttpConstants.GET_CAROUSELS)
    suspend fun getCarousels(@Query("populate") all: String): Result<CarouselRes>
}
