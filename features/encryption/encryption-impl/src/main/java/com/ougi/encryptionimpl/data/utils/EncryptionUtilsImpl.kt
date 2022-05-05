package com.ougi.encryptionimpl.data.utils

import android.util.Base64
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class EncryptionUtilsImpl @Inject constructor() : EncryptionUtils {

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

    override fun encryptViaSecretKey(data: String, key: SecretKey): Pair<String, ByteArray> {
        aesCipher.init(Cipher.ENCRYPT_MODE, key)
        return aesCipherDoFinal(data)
    }

    override fun decryptViaSecretKey(data: String, key: SecretKey, iv: ByteArray): String {
        val ivParameterSpec = IvParameterSpec(iv)
        aesCipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
        return aesCipherDoFinal(data).first
    }

    private fun rsaCipherDoFinal(data: String): String {
        val cipherBytes = doFinal(data, rsaCipher)
        return Base64.encodeToString(cipherBytes, Base64.DEFAULT)
    }

    private fun aesCipherDoFinal(data: String): Pair<String, ByteArray> {
        val cipherBytes = doFinal(data, aesCipher)
        return Base64.encodeToString(cipherBytes, Base64.DEFAULT) to aesCipher.iv
    }

    private fun doFinal(data: String, cipher: Cipher): ByteArray {
        val bytes = Base64.decode(data, Base64.DEFAULT)
        return cipher.doFinal(bytes)
    }

    companion object {
        private const val RSA = "RSA/ECB/PKCS1Padding"
        private const val AES = "AES/CBC/PKCS5Padding"
    }
}