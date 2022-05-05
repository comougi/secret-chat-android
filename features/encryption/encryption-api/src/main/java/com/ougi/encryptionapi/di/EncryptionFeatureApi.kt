package com.ougi.encryptionapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils

interface EncryptionFeatureApi : BaseFeatureApi {
    val hashUtils: HashUtils
    val keyGenerationUtils: KeyGenerationUtils
    val keyStorageUtils: KeyStorageUtils
    val encryptionUtils: EncryptionUtils
    val encryptionClientApi: EncryptionClientApi
}