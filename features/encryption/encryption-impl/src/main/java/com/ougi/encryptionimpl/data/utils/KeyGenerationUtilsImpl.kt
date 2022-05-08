package com.ougi.encryptionimpl.data.utils

import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.passwordscreenapi.data.PasswordScreenStarter
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

class KeyGenerationUtilsImpl @Inject constructor(
    private val hashUtils: HashUtils,
    private val passwordScreenStarter: PasswordScreenStarter
) : KeyGenerationUtils {

    override var secretKey: SecretKey? = null
        get() {
            if (field == null)
                passwordScreenStarter.startPasswordScreen()
            return field
        }

    override fun createAesKeyFromPass(pass: String): SecretKey {
        val salt = hashUtils.getSalt(pass)
        val passCharArray = pass.toCharArray()

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
        val spec = PBEKeySpec(passCharArray, salt, 2048, 256)

        val key = factory.generateSecret(spec)
        return SecretKeySpec(key.encoded, "AES")
    }

    override fun setAesKey(key: SecretKey) {
        secretKey = key
        INSTANCE = this
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

    companion object {

        private var INSTANCE: KeyGenerationUtils? = null

        fun getInstance(keyGenerationUtils: KeyGenerationUtils): KeyGenerationUtils {
            return INSTANCE ?: keyGenerationUtils.also { INSTANCE = it }
        }
    }

}