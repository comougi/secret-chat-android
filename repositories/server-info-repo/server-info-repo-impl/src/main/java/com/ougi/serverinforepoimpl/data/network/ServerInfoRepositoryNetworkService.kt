package com.ougi.serverinforepoimpl.data.network

import com.ougi.networkapi.data.NetworkService
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerInfoRepositoryNetworkService : NetworkService {

    @GET("connectToMessagingWebSocket")
    fun getMessagingWebSocketConnectionLink(@Query("userId") userId: String): Call<String>

    @GET("connectToPushWebSocket")
    fun getPushWebSocketConnectionLink(@Query("userId") userId: String): Call<String>

}