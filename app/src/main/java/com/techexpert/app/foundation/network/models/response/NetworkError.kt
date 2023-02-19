package com.techexpert.app.foundation.network.models.response

import kotlin.Exception

class NetworkError {
    class Error(message: String) : Exception(message)
    class ErrorWithResponse(errResponseString: String) : Exception(errResponseString)
    class NotConnected(message: String) : Exception(message)
    class Generic(message: String) : Exception(message)
    class RequestURLNotSupported(message: String) : Exception(message)
    class PreventedURLRequestFromBeingInitiated(message: String) : Exception()
    class RequestTimeOut(message: String) : Exception(message)
}
