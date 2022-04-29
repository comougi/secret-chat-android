package com.ougi.encryptionapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import com.ougi.encryptionapi.data.utils.KeyUtils

interface EncryptionFeatureApi : BaseFeatureApi {
    val hashUtils: HashUtils
    val keyUtils: KeyUtils
    val keyStorageUtils: KeyStorageUtils
    val encryptionUtils: EncryptionUtils
}