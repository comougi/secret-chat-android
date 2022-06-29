package com.ougi.coreutils.utils

import com.ougi.coreutils.di.CoreUtilsComponentHolder

object StringParser {
    fun parseToString(value: Any?): String? {
        val context = CoreUtilsComponentHolder.getInstance().contextProvider.context
        return when (value) {
            is Int -> context.getString(value)
            null -> null
            else -> value.toString()
        }
    }
}