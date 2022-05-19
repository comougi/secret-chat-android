package com.ougi.encryptionimpl.di

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.passwordscreenapi.data.PasswordScreenStarter

interface EncryptionFeatureDeps : BaseFeatureDeps {
    val context: Context
    val passwordScreenStarter: PasswordScreenStarter
}