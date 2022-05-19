package com.ougi.serverinforepoimpl.data.network

import com.ougi.networkapi.data.NetworkService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface ServerInfoRepositoryNetworkService : NetworkService {

    @GET("connectToWebSocket")
    fun getWebSocketConnectionLink(@Body userId: String): Call<String>

}