package com.ougi.chatrepoapi.data.network

import com.ougi.coreutils.utils.Result

interface ChatRepositoryNetworkApi {
    suspend fun createChatWithUsers(userId: String, userIds: List<String>): Result<String?>
}