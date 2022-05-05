package com.ougi.encryptionimpl.di

import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.passwordscreenapi.data.PasswordScreenStarter

interface EncryptionFeatureDeps : BaseFeatureDeps {
    val dataStoreClientApi: DataStoreClientApi
    val passwordScreenStarter: PasswordScreenStarter
}