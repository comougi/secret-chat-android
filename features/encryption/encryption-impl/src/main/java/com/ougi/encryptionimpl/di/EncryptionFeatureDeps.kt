package com.ougi.encryptionimpl.di

import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.datastoreapi.data.DataStoreClientApi

interface EncryptionFeatureDeps : BaseFeatureDeps {
    val dataStoreClientApi: DataStoreClientApi
}