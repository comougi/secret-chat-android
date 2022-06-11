package com.ougi.encryptionimpl.data

import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.entity.AesEncryptedData
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class EncryptionClientApiImpl @Inject constructor(
    private val encryptionUtils: EncryptionUtils,
    private val keyStorageApi: KeyStorageApi,
    private val keyGenerationUtils: KeyGenerationUtils
) : EncryptionClientApi {

    private val secretKey
        get() = keyGenerationUtils.secretKey!!

    override fun encryptViaRsaKey(data: String, publicKeyString: String): String {
        val publicKey = keyGenerationUtils.createPublicKeyFromString(publicKeyString, "RSA")
        return encryptionUtils.encryptViaPublicKey(data, publicKey)
    }

    override suspend fun decryptViaRsaKey(data: String): String {
        val rsaKeyPair = keyStorageApi.readRsaKeyPair()
        val private = rsaKeyPair.private
        return encryptionUtils.decryptViaPrivateKey(data, private)
    }

    override fun encryptViaSecretKey(data: String): String {
        val encryptedData = encryptionUtils.encryptViaSecretKey(data, secretKey)
        return Json.encodeToString(encryptedData)
    }

    override fun decryptViaSecretKey(data: AesEncryptedData): Pair<String, Boolean> {
        return encryptionUtils.decryptViaSecretKey(data, secretKey)
    }

    override suspend fun encryptViaDHAesKey(data: String): String {
        val secretKey = keyStorageApi.readDHAesKey()
        val encryptedData = encryptionUtils.encryptViaSecretKey(data, secretKey)
        return Json.encodeToString(encryptedData)
    }

    override suspend fun decryptViaDHAesKey(data: AesEncryptedData): Pair<String, Boolean> {
        val secretKey = keyStorageApi.readDHAesKey()
        return encryptionUtils.decryptViaSecretKey(data, secretKey)
    }

}