package com.ougi.encryptionimpl.data.utils

import android.util.Base64
import com.ougi.encryptionapi.data.entity.AesEncryptedData
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.HashUtils
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.inject.Inject

class EncryptionUtilsImpl @Inject constructor(private val hashUtils: HashUtils) : EncryptionUtils {

    private val rsaCipher = Cipher.getInstance(RSA)
    private val aesCipher = Cipher.getInstance(AES)

    override fun encryptViaPublicKey(data: String, publicKey: PublicKey): String {
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return encryptRsaCipherDoFinal(data)
    }

    private fun encryptRsaCipherDoFinal(data: String): String {
        val cipherBytes = encryptDoFinal(data, rsaCipher)
        return Base64.encodeToString(cipherBytes, Base64.NO_WRAP)
    }


    override fun decryptViaPrivateKey(data: String, privateKey: PrivateKey): String {
        rsaCipher.init(Cipher.DECRYPT_MODE, privateKey)
        return decryptRsaCipherDoFinal(data)
    }

    private fun decryptRsaCipherDoFinal(data: String): String {
        val cipherBytes = decryptDoFinal(data, rsaCipher)
        val cipherBase64Bytes = Base64.decode(cipherBytes, Base64.NO_WRAP)
        return cipherBase64Bytes.decodeToString()
    }

    private fun decryptDoFinal(data: String, cipher: Cipher): ByteArray {
        val bytes = Base64.decode(data, Base64.NO_WRAP)
        return cipher.doFinal(bytes)
    }


    override fun encryptViaSecretKey(data: String, key: SecretKey): AesEncryptedData {
        aesCipher.init(Cipher.ENCRYPT_MODE, key)

        val encryptedData = encryptAesCipherDoFinal(data)

        val hash = hashUtils.getHmac(data, key)

        return AesEncryptedData.Builder()
            .encryptedData(encryptedData.first)
            .hash(hash)
            .iv(encryptedData.second)
            .build()
    }

    private fun encryptAesCipherDoFinal(data: String): Pair<String, ByteArray> {
        val cipherBytes = encryptDoFinal(data, aesCipher)
        return Base64.encodeToString(cipherBytes, Base64.NO_WRAP) to aesCipher.iv
    }

    private fun encryptDoFinal(data: String, cipher: Cipher): ByteArray {
        val dataBytes = data.encodeToByteArray()
        val dataBase64Bytes = Base64.encode(dataBytes, Base64.NO_WRAP)
        return cipher.doFinal(dataBase64Bytes)
    }

    override fun decryptViaSecretKey(
        data: AesEncryptedData,
        key: SecretKey
    ): Pair<String, Boolean> {
        val encryptedData = data.encryptedData
        val ivBytes = data.ivBytes()
        val decryptedData = decryptViaSecretKey(encryptedData, key, ivBytes)
        val isHashEquals =
            data.hashBytes().contentEquals(hashUtils.getHmac(decryptedData, key))
        return decryptedData to isHashEquals
    }

    private fun decryptViaSecretKey(data: String, key: SecretKey, iv: ByteArray): String {
        val ivParameterSpec = IvParameterSpec(iv)
        aesCipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
        return decryptAesCipherDoFinal(data).first
    }

    private fun decryptAesCipherDoFinal(data: String): Pair<String, ByteArray> {
        val cipherBytes = decryptDoFinal(data, aesCipher)
        val cipherBase64Bytes = Base64.decode(cipherBytes, Base64.NO_WRAP)
        return cipherBase64Bytes.decodeToString() to aesCipher.iv
    }


    companion object {
        private const val RSA = "RSA/ECB/PKCS1Padding"
        private const val AES = "AES/CBC/PKCS5Padding"
    }
}