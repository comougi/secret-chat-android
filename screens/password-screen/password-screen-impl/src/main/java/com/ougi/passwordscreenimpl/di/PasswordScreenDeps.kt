package com.ougi.passwordscreenimpl.di

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils

interface PasswordScreenDeps : BaseFeatureDeps {
    val keyStorageUtils: KeyStorageUtils
    val keyGenerationUtils: KeyGenerationUtils
    val context: Context
}