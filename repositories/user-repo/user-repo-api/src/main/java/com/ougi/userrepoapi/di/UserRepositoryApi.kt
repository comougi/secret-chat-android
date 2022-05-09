package com.ougi.userrepoapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import com.ougi.userrepoapi.data.network.UserRepositoryNetworkApi
import com.ougi.userrepoapi.data.repository.UserRepository

interface UserRepositoryApi : BaseFeatureApi {
    val userRepositoryDataStoreApi: UserRepositoryDataStoreApi
    val userRepositoryNetworkApi: UserRepositoryNetworkApi
    val userRepository: UserRepository
}