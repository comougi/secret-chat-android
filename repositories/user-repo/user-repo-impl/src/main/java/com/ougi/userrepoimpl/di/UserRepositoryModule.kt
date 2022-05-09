package com.ougi.userrepoimpl.di

import com.ougi.coreutils.dagger.Repo
import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import com.ougi.userrepoapi.data.network.UserRepositoryNetworkApi
import com.ougi.userrepoapi.data.repository.UserRepository
import com.ougi.userrepoimpl.data.datastore.UserRepositoryDataStoreApiImpl
import com.ougi.userrepoimpl.data.network.UserRepositoryNetworkApiImpl
import com.ougi.userrepoimpl.data.network.UserRepositoryNetworkService
import com.ougi.userrepoimpl.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface UserRepositoryModule {

    @[Repo Binds]
    fun bindUserRepositoryNetworkApi(userRepositoryNetworkApiImpl: UserRepositoryNetworkApiImpl): UserRepositoryNetworkApi

    @[Repo Binds]
    fun bindUserRepositoryDataStoreApi(userRepositoryDataStoreApiImpl: UserRepositoryDataStoreApiImpl): UserRepositoryDataStoreApi

    @[Repo Binds]
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    companion object {
        @[Repo Provides]
        fun providesUserRepositoryNetworkService(networkClientApi: NetworkClientApi): UserRepositoryNetworkService {
            return networkClientApi.create(UserRepositoryNetworkService::class.java)
        }
    }
}