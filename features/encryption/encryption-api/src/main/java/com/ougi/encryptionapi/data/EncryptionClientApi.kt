package com.ougi.encryptionapi.data

import com.ougi.encryptionapi.data.entity.AesEncryptedData

interface EncryptionClientApi {
    fun encryptViaRsaKey(data: String, publicKeyString: String): String
    suspend fun decryptViaRsaKey(data: String): String
    fun encryptViaSecretKey(data: String): String
    fun decryptViaSecretKey(data: AesEncryptedData): Pair<String, Boolean>
    suspend fun encryptViaDHAesKey(data: String): String
    suspend fun decryptViaDHAesKey(data: AesEncryptedData): Pair<String, Boolean>
}