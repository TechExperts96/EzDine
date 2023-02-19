package com.techexpert.app.foundation.network.models.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("context") val context: String,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
