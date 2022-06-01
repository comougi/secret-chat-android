package com.ougi.chatrepoimpl.data.network

import com.ougi.networkapi.data.NetworkService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatRepositoryNetworkService : NetworkService {

    @POST("createChatWithUsers")
    fun createChatWithUsers(
        @Query("userId") userId: String,
        @Body userIds: List<String>
    ): Call<String>
}