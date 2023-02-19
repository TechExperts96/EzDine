package com.techexpert.app.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techexpert.app.foundation.network.models.response.ErrorResponse
import okhttp3.ResponseBody

class ErrorResponseUtil {
    companion object {
        val gson = Gson()
        private val type = object : TypeToken<ErrorResponse>() {}.type

        fun getErrorBody(responseBody: ResponseBody): ErrorResponse {
            return gson.fromJson(responseBody.charStream(), type)
        }

        fun getErrorBody(response: String): ErrorResponse {
            return gson.fromJson(response, type)
        }
    }
}
