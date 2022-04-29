package com.ougi.encryptionimpl.data.utils

import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyUtils
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.KeyAgreement
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class KeyUtilsImpl @Inject constructor(private val hashUtils: HashUtils) : KeyUtils {

    override var secretKey: SecretKey? = null

    override fun setAesKeyFromPass(pass: String): SecretKey {
        val salt = hashUtils.getSalt(pass)
        val passCharArray = pass.toCharArray()

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
        val spec = PBEKeySpec(passCharArray, salt, 2048, 512)

        val key = factory.generateSecret(spec)
        return SecretKeySpec(key.encoded, "AES")
            .also { secretKey = it }
    }

    override fun generateRsaKeyPair(): KeyPair {
        val rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        rsaKeyPairGenerator.initialize(512)
        return rsaKeyPairGenerator.genKeyPair()
    }

    override fun generateDHKeyPair(): KeyPair {
        val dhKeyPairGenerator = KeyPairGenerator.getInstance("DiffieHellman")
        dhKeyPairGenerator.initialize(512)
        return dhKeyPairGenerator.generateKeyPair()
    }

    override fun generateDHAesSecretKey(publicKey: PublicKey, privateKey: PrivateKey): SecretKey {
        val keyAgreement = KeyAgreement.getInstance("DH")
        keyAgreement.init(privateKey)
        keyAgreement.doPhase(publicKey, true)

        val key = keyAgreement.generateSecret()
        return SecretKeySpec(key, "AES")
    }

}