package com.ougi.userrepoimpl.di

import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import com.ougi.networkapi.data.NetworkClientApi

interface UserRepositoryDeps : BaseFeatureDeps {
    val dataStoreClientApi: DataStoreClientApi
    val networkClientApi: NetworkClientApi
    val encryptionClientApi: EncryptionClientApi
    val keyStorageUtils: KeyStorageUtils
}