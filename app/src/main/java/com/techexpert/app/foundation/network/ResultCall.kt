package com.techexpert.app.foundation.network

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.techexpert.app.foundation.network.models.response.DataTransferError
import com.techexpert.app.foundation.network.models.response.ErrorModel
import com.techexpert.app.foundation.network.models.response.ErrorResponse
import com.techexpert.app.foundation.network.models.response.NetworkError
import com.techexpert.app.util.ErrorResponseUtil
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(private val delegate: Call<T>) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(response.code(), Result.success(response.body()!!))
                        )
                    } else {
                        if (response.body().toString().isNotEmpty()) {
                            try {
                                val responseBody = response.errorBody() as ResponseBody
                                val errorResponse: ErrorResponse =
                                    ErrorResponseUtil.getErrorBody(responseBody)
                                val errorResponseString = Gson().toJson(ErrorModel(errorResponse))
                                callback.onResponse(
                                    this@ResultCall,
                                    Response.success(
                                        Result.failure(
                                            NetworkError.ErrorWithResponse(errorResponseString)
                                        )
                                    )
                                )
                            } catch (e: Exception) {
                                callback.onResponse(
                                    this@ResultCall,
                                    Response.success(Result.failure(NetworkError.Error("Looks like something went wrong.\nWe are working on getting it fixed.")))
                                )
                            }
                        } else {
                            callback.onResponse(
                                this@ResultCall,
                                Response.success(Result.failure(DataTransferError.NoResponse("No response from the server.\nPlease try again later.")))
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    var errMsg = ""
                    t.message?.let { errMsg = it }

                    when (t) {
                        is JsonSyntaxException -> callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(DataTransferError.ParsingError(errMsg)))
                        )
                        is HttpException -> callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(DataTransferError.NetworkFailure(errMsg)))
                        )
                        is IOException -> callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(NetworkError.NotConnected(errMsg)))
                        )
                        else -> callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(NetworkError.Generic(errMsg)))
                        )
                    }
                }
            }
        )
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun execute(): Response<Result<T>> {
        return Response.success(Result.success(delegate.execute().body()!!))
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun clone(): Call<Result<T>> {
        return ResultCall(delegate.clone())
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }
}
