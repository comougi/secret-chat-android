package com.ougi.passwordscreenimpl.data.repository

import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import com.ougi.passwordscreenimpl.domain.repository.EnterPasswordRepository
import javax.crypto.SecretKey
import javax.inject.Inject

class EnterPasswordRepositoryImpl @Inject constructor(
    private val keyGenerationUtils: KeyGenerationUtils,
    private val keyStorageUtils: KeyStorageUtils
) : EnterPasswordRepository {


    override suspend fun hasPassword(): Boolean {
        return keyStorageUtils.hasPassword()
    }

    override fun setAesKey(secretKey: SecretKey) {
        keyGenerationUtils.setAesKey(secretKey)
    }

    override suspend fun isPasswordValid(password: String): Pair<Boolean, SecretKey?> {
        val secretKey = keyGenerationUtils.createAesKeyFromPass(password)
        return try {
            val storedPassword = keyStorageUtils.readPassword(secretKey)?.trim()
            if (storedPassword == password) true to secretKey
            else false to null
        } catch (e: Exception) {
            false to null
        }
    }

}