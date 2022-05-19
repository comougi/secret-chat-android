package com.ougi.encryptionimpl.data

import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import javax.inject.Inject

class EncryptionClientApiImpl @Inject constructor(
    private val encryptionUtils: EncryptionUtils,
    private val keyStorageApi: KeyStorageApi,
    private val keyGenerationUtils: KeyGenerationUtils
) : EncryptionClientApi {

    private val secretKey
        get() = keyGenerationUtils.secretKey!!

    override suspend fun encryptViaRsaKey(data: String): String {
        val rsaKeyPair = keyStorageApi.readRsaKeyPair()
        val public = rsaKeyPair.public
        return encryptionUtils.encryptViaPublicKey(data, public)
    }

    override suspend fun decryptViaRsaKey(data: String): String {
        val rsaKeyPair = keyStorageApi.readRsaKeyPair()
        val private = rsaKeyPair.private
        return encryptionUtils.decryptViaPrivateKey(data, private)
    }

    override fun encryptViaSecretKey(data: String): String {
        return encryptionUtils.encryptViaSecretKeySeparated(data, secretKey)
    }

    override fun decryptViaSecretKey(data: String): Pair<String, Boolean> {
        return encryptionUtils.decryptViaSecretKeyDivided(data, secretKey)
    }

    override suspend fun encryptViaDHAesKey(data: String): String {
        val secretKey = keyStorageApi.readDHAesKey()
        return encryptionUtils.encryptViaSecretKeySeparated(data, secretKey)
    }

    override suspend fun decryptViaDHAesKey(data: String): Pair<String, Boolean> {
        val secretKey = keyStorageApi.readDHAesKey()
        return encryptionUtils.decryptViaSecretKeyDivided(data, secretKey)
    }

}