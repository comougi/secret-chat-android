package com.ougi.encryptionapi.data.utils

import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

interface EncryptionUtils {
    fun encryptViaPublicKey(data: String, publicKey: PublicKey): String
    fun decryptViaPrivateKey(data: String, privateKey: PrivateKey): String
    fun encryptViaSecretKey(data: String, key: SecretKey): Pair<String, ByteArray>
    fun decryptViaSecretKey(data: String, key: SecretKey, iv: ByteArray): String
    fun encryptViaSecretKeySeparated(data: String, key: SecretKey): String
    fun decryptViaSecretKeyDivided(data: String, key: SecretKey): Pair<String, Boolean>
}