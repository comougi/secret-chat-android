package com.ougi.coreutils.utils

sealed class Result<T> private constructor(data: T?, val message: Any?) {

    fun message(): String? = message as? String

    class Success<T>(val data: T?, message: Any? = null) :
        Result<T>(data, StringParser.parseToString(message))

    class Error<T>(message: Any? = null) :
        Result<T>(Any() as T, StringParser.parseToString(message))

    class Loading<T>(message: Any? = null) :
        Result<T>(Any() as T, StringParser.parseToString(message))

}