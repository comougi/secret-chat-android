package com.ougi.passwordscreenimpl.data.repository

import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.passwordscreenimpl.domain.repository.PasswordRepository
import javax.crypto.SecretKey
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val keyGenerationUtils: KeyGenerationUtils,
    private val keyStorageApi: KeyStorageApi
) : PasswordRepository {

    override suspend fun savePassword(password: String) {
        val secretKey = keyGenerationUtils.createAesKeyFromPass(password)
        keyGenerationUtils.setAesKey(secretKey)
        keyStorageApi.savePassword(password)
        keyStorageApi.readDhKeyPair()
        keyStorageApi.readRsaKeyPair()
    }

    override suspend fun isHasPassword(): Boolean {
        return keyStorageApi.hasPassword()
    }

    override suspend fun setAesKey(secretKey: SecretKey) {
        keyGenerationUtils.setAesKey(secretKey)
    }

    override suspend fun isPasswordValid(password: String): Pair<Boolean, SecretKey?> {
        val secretKey = keyGenerationUtils.createAesKeyFromPass(password)
        keyGenerationUtils.setAesKey(secretKey)
        return try {
            val storedPassword = keyStorageApi.readPassword()?.trim()
            if (storedPassword == password) true to secretKey
            else false to null
        } catch (e: Exception) {
            false to null
        }
    }

}