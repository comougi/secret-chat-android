package com.ougi.encryptionapi.data

interface EncryptionClientApi {
    suspend fun encryptViaRsaKey(data: String): String
    suspend fun decryptViaRsaKey(data: String): String
    fun encryptViaSecretKey(data: String): Pair<String, ByteArray>
    fun decryptViaSecretKey(data: String, iv: ByteArray): String
    suspend fun encryptViaDhKey(data: String): Pair<String, ByteArray>
    suspend fun decryptViaDhKey(data: String, iv: ByteArray): String
}