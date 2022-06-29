package com.ougi.encryptionimpl.data.utils

import android.util.Base64
import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import java.security.*
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyAgreement
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class KeyGenerationUtilsImpl @Inject constructor(
    private val hashUtils: HashUtils
) : KeyGenerationUtils {

    override var secretKey: SecretKey? = null

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
        rsaKeyPairGenerator.initialize(2048)
        return rsaKeyPairGenerator.genKeyPair()
    }

    override fun generateDHKeyPair(): KeyPair {
        val dhKeyPairGenerator = KeyPairGenerator.getInstance("DH")
        dhKeyPairGenerator.initialize(512)
        return dhKeyPairGenerator.generateKeyPair()
    }

    override fun generateDHAesSecretKey(publicKey: PublicKey, privateKey: PrivateKey): SecretKey {
        val keyAgreement = KeyAgreement.getInstance("DH")
        keyAgreement.init(privateKey)
        keyAgreement.doPhase(publicKey, true)

        val key = keyAgreement.generateSecret()
        val sha256: MessageDigest = MessageDigest.getInstance("SHA-256")
        val keyBytes: ByteArray = sha256.digest(key).copyOf(32)
        return SecretKeySpec(keyBytes, "AES")
    }

    override fun createPublicKeyFromString(publicKeyString: String, algorithm: String): PublicKey {
        val publicKeyBytes = Base64.decode(publicKeyString, Base64.NO_WRAP)
        val keyFactory = KeyFactory.getInstance(algorithm)
        return keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes))
    }

    override fun generateAesKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    companion object {

        private var INSTANCE: KeyGenerationUtils? = null

        fun getInstance(keyGenerationUtils: KeyGenerationUtils): KeyGenerationUtils {
            return INSTANCE ?: keyGenerationUtils.also { INSTANCE = it }
        }
    }

}