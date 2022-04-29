package com.ougi.encryptionapi.data.utils

import java.security.Key

interface HashUtils {
    fun generateSha512Hash(from: String): String
    fun getSalt(from: String): ByteArray
    fun getHmac(string: String, key: Key): String
    fun getHmac(bytes: ByteArray, key: Key): String
}