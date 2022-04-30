package com.ougi.encryptionapi.data.utils

import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

interface EncryptionUtils {
    fun encryptViaPublicKey(data: String, publicKey: PublicKey): String
    suspend fun encryptViaRsaPublicKey(data: String): String
    fun decryptViaPrivateKey(data: String, privateKey: PrivateKey): String
    suspend fun decryptViaRsaPrivateKey(data: String): String
    fun encryptViaSecretKey(
        data: String,
        secretKey: SecretKey,
        iv: ByteArray
    ): Pair<String, ByteArray>

    fun encryptViaSecretKey(data: String, iv: ByteArray): Pair<String, ByteArray>
    fun decryptViaSecretKey(data: String, secretKey: SecretKey): String
    fun decryptViaSecretKey(data: String): String
}