package com.jumia.myapplication.infrastructure

/**
 * Exception throw by the application when a there is a network connection exception.
 */
class NetworkException : RuntimeException {

    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}