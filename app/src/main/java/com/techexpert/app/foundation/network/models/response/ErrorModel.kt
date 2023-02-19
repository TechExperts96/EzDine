package com.techexpert.app.foundation.network.models.response

class ErrorModel {
    constructor()

    constructor(item: ErrorResponse) {
        item.context.let { context = it }
        item.code.let { code = it }
        item.message.let { message = it }
    }

    var context = ""
    var code = ""
    var message = ""
}
