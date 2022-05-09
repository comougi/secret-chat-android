package com.ougi.serverinforepoimpl.data.network

import com.ougi.networkapi.data.NetworkService
import com.ougi.serverinforepoapi.data.entities.WebSocketInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerInfoRepositoryNetworkService : NetworkService {

    @GET("webSocketInfo/{userId}")
    fun getWebSocketInfo(@Path("userId") userId: String): Call<WebSocketInfo>

}