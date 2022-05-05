package com.ougi.encryptionapi.data.utils

import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

interface KeyGenerationUtils {
    var secretKey: SecretKey?
    fun createAesKeyFromPass(pass: String): SecretKey
    fun setAesKey(key: SecretKey)
    fun generateRsaKeyPair(): KeyPair
    fun generateDHKeyPair(): KeyPair
    fun generateDHAesSecretKey(publicKey: PublicKey, privateKey: PrivateKey): SecretKey
}