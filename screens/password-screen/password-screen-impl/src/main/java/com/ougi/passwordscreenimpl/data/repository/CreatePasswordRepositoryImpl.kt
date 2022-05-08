package com.ougi.passwordscreenimpl.data.repository

import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import com.ougi.passwordscreenimpl.domain.repository.CreatePasswordRepository
import javax.inject.Inject

class CreatePasswordRepositoryImpl @Inject constructor(
    private val keyStorageUtils: KeyStorageUtils,
    private val keyGenerationUtils: KeyGenerationUtils
) : CreatePasswordRepository {

    override suspend fun createPassword(password: String) {
        val secretKey = keyGenerationUtils.createAesKeyFromPass(password)
        keyGenerationUtils.setAesKey(secretKey)
        keyStorageUtils.savePassword(password, secretKey)
        keyStorageUtils.readDhKeyPair()
        keyStorageUtils.readRsaKeyPair()
    }

}