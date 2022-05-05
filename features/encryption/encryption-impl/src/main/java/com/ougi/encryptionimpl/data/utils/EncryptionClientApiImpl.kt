package com.ougi.encryptionimpl.data.utils

import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import javax.inject.Inject

class EncryptionClientApiImpl @Inject constructor(
    private val encryptionUtils: EncryptionUtils,
    private val keyStorageUtils: KeyStorageUtils,
    private val keyGenerationUtils: KeyGenerationUtils
) : EncryptionClientApi {

    private val secretKey by lazy { keyGenerationUtils.secretKey!! }

    override suspend fun encryptViaRsaKey(data: String): String {
        val rsaKeyPair = keyStorageUtils.readRsaKeyPair()
        val public = rsaKeyPair.public
        return encryptionUtils.encryptViaPublicKey(data, public)
    }

    override suspend fun decryptViaRsaKey(data: String): String {
        val rsaKeyPair = keyStorageUtils.readRsaKeyPair()
        val private = rsaKeyPair.private
        return encryptionUtils.decryptViaPrivateKey(data, private)
    }

    override fun encryptViaSecretKey(data: String): Pair<String, ByteArray> {
        return encryptionUtils.encryptViaSecretKey(data, secretKey)
    }

    override fun decryptViaSecretKey(data: String, iv: ByteArray): String {
        return encryptionUtils.decryptViaSecretKey(data, secretKey, iv)
    }

    override suspend fun encryptViaDhKey(data: String): Pair<String, ByteArray> {
        val dhKeyPair = keyStorageUtils.readDhKeyPair()
        val dhKey = keyGenerationUtils.generateDHAesSecretKey(dhKeyPair.public, dhKeyPair.private)
        return encryptionUtils.encryptViaSecretKey(data, dhKey)
    }

    override suspend fun decryptViaDhKey(data: String, iv: ByteArray): String {
        val dhKeyPair = keyStorageUtils.readDhKeyPair()
        val dhKey = keyGenerationUtils.generateDHAesSecretKey(dhKeyPair.public, dhKeyPair.private)
        return encryptionUtils.decryptViaSecretKey(data, dhKey, iv)
    }
}