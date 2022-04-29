package com.ougi.encryptionapi.data.utils

import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.SecretKey

interface KeyUtils {
    fun getAesKeyFromPass(pass: String): SecretKey
    fun generateRsaKeyPair(): KeyPair
    fun generateDHKeyPair(): KeyPair
    fun generateDHAesSecretKey(publicKey: PublicKey, privateKey: PrivateKey): SecretKey
}