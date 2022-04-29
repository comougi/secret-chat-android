package com.ougi.encryptionimpl.data.utils

import com.ougi.encryptionapi.data.utils.EncryptionUtils
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class EncryptionUtilsImpl : EncryptionUtils {

    private val rsaCipher = Cipher.getInstance(RSA)
    private val aesCipher = Cipher.getInstance(AES)

    override fun encryptViaPublicKey(data: String, publicKey: PublicKey): String {
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return rsaCipherDoFinal(data)
    }

    override fun decryptViaPrivateKey(data: String, privateKey: PrivateKey): String {
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey)
        return rsaCipherDoFinal(data)
    }

    override fun encryptViaSecretKey(
        data: String,
        secretKey: SecretKey,
        iv: ByteArray
    ): Pair<String, ByteArray> {
        val ivParameterSpec = IvParameterSpec(iv)
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
        return aesCipherDoFinal(data)
    }

    override fun decryptViaSecretKey(data: String, secretKey: SecretKey): String {
        aesCipher.init(Cipher.DECRYPT_MODE, secretKey)
        return aesCipherDoFinal(data).first
    }

    private fun rsaCipherDoFinal(data: String): String {
        val cipherBytes = doFinal(data, rsaCipher)
        return cipherBytes.decodeToString()
    }

    private fun aesCipherDoFinal(data: String): Pair<String, ByteArray> {
        val cipherBytes = doFinal(data, aesCipher)
        return cipherBytes.decodeToString() to aesCipher.iv
    }

    private fun doFinal(data: String, cipher: Cipher): ByteArray {
        val bytes = data.encodeToByteArray()
        return cipher.doFinal(bytes)
    }

    companion object {
        private const val RSA = "RSA/ECB/PKCS5Padding"
        private const val AES = "AES/CBC/PKCS5Padding"
    }
}