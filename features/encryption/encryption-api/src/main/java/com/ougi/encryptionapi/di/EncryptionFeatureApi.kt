package com.ougi.encryptionapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.encryptionapi.data.EncryptedDataStoreApi
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils

interface EncryptionFeatureApi : BaseFeatureApi {
    val keyStorageApi: KeyStorageApi
    val encryptionClientApi: EncryptionClientApi
    val encryptedDataStoreApi: EncryptedDataStoreApi
    val hashUtils: HashUtils
    val keyGenerationUtils: KeyGenerationUtils
    val encryptionUtils: EncryptionUtils
}