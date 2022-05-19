package com.ougi.passwordscreenimpl.data.repository

import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.passwordscreenimpl.domain.repository.EnterPasswordRepository
import javax.crypto.SecretKey
import javax.inject.Inject

class EnterPasswordRepositoryImpl @Inject constructor(
    private val keyGenerationUtils: KeyGenerationUtils,
    private val keyStorageApi: KeyStorageApi
) : EnterPasswordRepository {


    override suspend fun hasPassword(): Boolean {
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