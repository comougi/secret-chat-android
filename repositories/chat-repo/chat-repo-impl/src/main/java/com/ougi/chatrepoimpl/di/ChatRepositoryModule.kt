package com.ougi.chatrepoimpl.di

import com.ougi.chatrepoapi.data.database.ChatRepositoryDao
import com.ougi.chatrepoapi.data.network.ChatRepositoryNetworkApi
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.chatrepoimpl.data.database.ChatRepositoryDatabase
import com.ougi.chatrepoimpl.data.network.ChatRepositoryNetworkApiImpl
import com.ougi.chatrepoimpl.data.network.ChatRepositoryNetworkService
import com.ougi.chatrepoimpl.data.repository.ChatRepositoryImpl
import com.ougi.coreutils.dagger.Repo
import com.ougi.dbapi.data.DbClientApi
import com.ougi.networkapi.data.NetworkClientApi
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface ChatRepositoryModule {

    @[Repo Binds]
    fun bindChatRepositoryNetworkApi(chatRepositoryNetworkApiImpl: ChatRepositoryNetworkApiImpl): ChatRepositoryNetworkApi

    @[Repo Binds]
    fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    companion object {
        @[Repo Provides]
        fun provideChatRepositoryNetworkService(networkClientApi: NetworkClientApi): ChatRepositoryNetworkService {
            return networkClientApi.create(ChatRepositoryNetworkService::class.java)
        }

        @[Repo Provides]
        fun provideChatRepositoryDatabase(dbClientApi: DbClientApi): ChatRepositoryDatabase {
            return ChatRepositoryDatabase.getDatabaseClient(dbClientApi)
        }

        @[Repo Provides]
        fun provideChatRepositoryDatabaseDao(chatRepositoryDatabase: ChatRepositoryDatabase): ChatRepositoryDao {
            return chatRepositoryDatabase.chatRepositoryDatabaseDao
        }

    }
}