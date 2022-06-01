package com.ougi.chatrepoapi.di

import com.ougi.chatrepoapi.data.database.ChatRepositoryDao
import com.ougi.chatrepoapi.data.network.ChatRepositoryNetworkApi
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.corecommon.base.di.BaseFeatureApi

interface ChatRepositoryApi : BaseFeatureApi {
    val chatRepository: ChatRepository
    val chatRepositoryNetworkApi: ChatRepositoryNetworkApi
    val chatRepositoryDao: ChatRepositoryDao
}