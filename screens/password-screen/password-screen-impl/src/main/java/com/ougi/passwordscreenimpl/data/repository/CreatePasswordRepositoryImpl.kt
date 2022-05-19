package com.ougi.passwordscreenimpl.data.repository

import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.passwordscreenimpl.domain.repository.CreatePasswordRepository
import javax.inject.Inject

class CreatePasswordRepositoryImpl @Inject constructor(
    private val keyStorageApi: KeyStorageApi,
    private val keyGenerationUtils: KeyGenerationUtils
) : CreatePasswordRepository {

    override suspend fun savePassword(password: String) {
        val secretKey = keyGenerationUtils.createAesKeyFromPass(password)
        keyGenerationUtils.setAesKey(secretKey)
        keyStorageApi.savePassword(password)
        keyStorageApi.readDhKeyPair()
        keyStorageApi.readRsaKeyPair()
    }

}