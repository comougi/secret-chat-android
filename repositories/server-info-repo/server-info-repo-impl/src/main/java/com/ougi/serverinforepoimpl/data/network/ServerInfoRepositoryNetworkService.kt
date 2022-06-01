package com.ougi.serverinforepoimpl.data.network

import com.ougi.networkapi.data.NetworkService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerInfoRepositoryNetworkService : NetworkService {

    @POST("connectToMessagingWebSocket")
    fun getMessagingWebSocketConnectionLink(
        @Query("userId") userId: String,
        @Body userIdEncrypted: String
    ): Call<String>

    @GET("connectToPushWebSocket")
    fun getPushWebSocketConnectionLink(@Query("userId") userId: String): Call<String>

}