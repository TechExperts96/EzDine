package com.techexpert.app.foundation.network.models.response

class DataTransferError {
    class NoResponse(message: String) : Exception()
    class ParsingError(message: String) : Exception(message)
    class NetworkFailure(message: String) : Exception(message)
    class ResolvedNetworkFailure(message: String) : Exception(message)
    class FailedToDecodeData(message: String) : Exception(message)
}
