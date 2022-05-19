package com.ougi.encryptionapi.data.utils

import java.security.Key

interface HashUtils {
    fun generateSha512Hash(from: String): String
    fun getSalt(from: String): ByteArray
    fun getHmac(string: String, key: Key): ByteArray
    fun getHmac(bytes: ByteArray, key: Key): ByteArray
}