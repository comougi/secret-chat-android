package com.ougi.encryptionapi.data

interface EncryptionClientApi {
    suspend fun encryptViaRsaKey(data: String): String
    suspend fun decryptViaRsaKey(data: String): String
    fun encryptViaSecretKey(data: String): String
    fun decryptViaSecretKey(data: String): Pair<String, Boolean>
    suspend fun encryptViaDHAesKey(data: String): String
    suspend fun decryptViaDHAesKey(data: String): Pair<String, Boolean>
}