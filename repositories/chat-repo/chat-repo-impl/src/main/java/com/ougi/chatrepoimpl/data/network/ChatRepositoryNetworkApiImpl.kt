package com.ougi.chatrepoimpl.data.network

import com.ougi.chatrepoapi.data.network.ChatRepositoryNetworkApi
import com.ougi.coreutils.utils.Result
import com.ougi.networkapi.data.utils.SafeApiCallUtils
import javax.inject.Inject

class ChatRepositoryNetworkApiImpl @Inject constructor(private val chatRepositoryNetworkService: ChatRepositoryNetworkService) :
    ChatRepositoryNetworkApi {

    override suspend fun createChatWithUsers(
        userId: String,
        userIds: String
    ): Result<String?> {
        val call = chatRepositoryNetworkService.createChatWithUsers(userId, userIds)
        return SafeApiCallUtils.safeApiCall(call)
    }
}