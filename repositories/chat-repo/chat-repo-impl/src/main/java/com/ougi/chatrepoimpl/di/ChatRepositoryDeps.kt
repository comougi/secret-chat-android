package com.ougi.chatrepoimpl.di

import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.dbapi.data.DbClientApi
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi

interface ChatRepositoryDeps : BaseFeatureDeps {
    val userRepositoryDataString: UserRepositoryDataStoreApi
    val encryptionClientApi: EncryptionClientApi
    val networkClientApi: NetworkClientApi
    val dbClientApi: DbClientApi
}