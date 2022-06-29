package com.ougi.encryptionapi.data.utils

import com.ougi.encryptionapi.data.entity.AesEncryptedData
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

interface EncryptionUtils {
    fun encryptViaPublicKey(data: String, publicKey: PublicKey): String
    fun decryptViaPrivateKey(data: String, privateKey: PrivateKey): String

    fun decryptViaSecretKey(data: AesEncryptedData, key: SecretKey): Pair<String, Boolean>
    fun encryptViaSecretKey(data: String, key: SecretKey): AesEncryptedData
}