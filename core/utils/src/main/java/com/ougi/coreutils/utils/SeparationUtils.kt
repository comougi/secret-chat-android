package com.ougi.coreutils.utils

import com.ougi.corecommon.Constants
import java.util.*

object SeparationUtils {

    fun separate(vararg strings: String): String {
        return if (strings.none { str -> str.contains(Constants.SEPARATOR) })
            strings.joinToString(Constants.SEPARATOR)
        else
            randomSeparation(strings)
    }

    private fun randomSeparation(strings: Array<out String>): String {
        val randomSeparator = " ${UUID.randomUUID()} "
        return if (strings.none { str -> str.contains(randomSeparator) })
            strings.joinToString(randomSeparator) + "$SEPARATORS_SEPARATOR$randomSeparator"
        else
            randomSeparation(strings)
    }

    fun divide(string: String): List<String> {
        var splittedString = string.split(Constants.SEPARATOR)
        return if (splittedString.size > 2 || string.contains(SEPARATORS_SEPARATOR)) {
            splittedString = string.split(SEPARATORS_SEPARATOR)
            val separator = splittedString.last()
            string
                .take(string.length - SEPARATORS_SEPARATOR.length - separator.length)
                .split(separator)
        } else
            splittedString
    }

    private const val SEPARATORS_SEPARATOR = " SEPARATOR : "
}