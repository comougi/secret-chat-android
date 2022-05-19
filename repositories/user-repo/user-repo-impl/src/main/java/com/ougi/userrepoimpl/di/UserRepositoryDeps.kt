package com.ougi.userrepoimpl.di

import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.encryptionapi.data.EncryptedDataStoreApi
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.networkapi.data.NetworkClientApi

interface UserRepositoryDeps : BaseFeatureDeps {
    val networkClientApi: NetworkClientApi
    val encryptionClientApi: EncryptionClientApi
    val encryptedDataStoreApi: EncryptedDataStoreApi
    val keyStorageApi: KeyStorageApi
}