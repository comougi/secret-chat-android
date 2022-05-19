package com.ougi.coreutils.utils

sealed class Result<T> private constructor(data: T?, val message: Any?) {

    fun message(): String = message.toString()

    class Success<T>(val data: T?, message: Any? = null) :
        Result<T>(data, StringParser.parseToString(message))

    class Error<T>(message: Any? = null) :
        Result<T?>(null, StringParser.parseToString(message))

    class Loading<T>(message: Any? = null) :
        Result<T?>(null, StringParser.parseToString(message))

}