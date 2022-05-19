package com.ougi.serverinforepoimpl.di

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi

interface ServerInfoRepositoryDeps : BaseFeatureDeps {
    val context: Context
    val networkClientApi: NetworkClientApi
    val userRepositoryDataStoreApi: UserRepositoryDataStoreApi
    val encryptionClientApi: EncryptionClientApi
}