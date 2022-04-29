package com.ougi.encryptionapi.data.utils

import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

interface EncryptionUtils {
    fun encryptViaPublicKey(data: String, publicKey: PublicKey): String
    fun decryptViaPrivateKey(data: String, privateKey: PrivateKey): String
    fun encryptViaSecretKey(
        data: String,
        secretKey: SecretKey,
        iv: ByteArray
    ): Pair<String, ByteArray>

    fun decryptViaSecretKey(data: String, secretKey: SecretKey): String
}