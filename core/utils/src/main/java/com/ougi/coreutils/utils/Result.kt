package com.ougi.coreutils.utils

import com.ougi.coreutils.di.CoreUtilsComponentHolder

sealed class Result<T> private constructor(data: T?, private val message: Any?) {

    class Success<T>(val data: T?, message: Any? = null) : Result<T>(data, setMessage(message))

    class Error<T>(message: Any? = null) : Result<T?>(null, setMessage(message))

    class Loading<T>(message: Any? = null) : Result<T?>(null, setMessage(message))

    fun message(): String? = message as? String

    companion object {

        private fun setMessage(message: Any?): String? {
            val context = CoreUtilsComponentHolder.getInstance().contextProvider.context
            return when (message) {
                is Int? -> message?.let { context.getString(it) }
                is String? -> message
                else -> throw IllegalAccessException("Put only 'Int' and 'String' arguments")
            }
        }
    }

}