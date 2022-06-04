package com.ougi.encryptionimpl.data.utils

import android.util.Base64
import com.ougi.encryptionapi.data.entity.DividedData
import kotlin.random.Random

object EncryptionSeparationUtils {

    fun separate(data: String, iv: ByteArray, hash: ByteArray): String {
        val ivString = Base64.encodeToString(iv, Base64.NO_WRAP)
        val hashString = Base64.encodeToString(hash, Base64.NO_WRAP)
        val randomString = " " + Base64.encodeToString(Random.nextBytes(8), Base64.NO_WRAP) + " "

        return if (!(data + ivString + hashString).contains(randomString))
            data + randomString + ivString + randomString + hashString + SEPARATOR + randomString
        else
            separate(data, iv, hash)
    }

    fun divide(separated: String): DividedData {
        var splittedData = separated.split(SEPARATOR)
        val separator = splittedData.last()
        val formattedData = separated.take(separated.length - SEPARATOR.length - separator.length)
        splittedData = formattedData.split(separator)
        val data = splittedData[0]
        val iv = Base64.decode(splittedData[1], Base64.NO_WRAP)
        val hash = Base64.decode(splittedData[2], Base64.NO_WRAP)
        return DividedData(data, iv, hash)
    }

    private const val SEPARATOR = " SEPARATOR : "
}