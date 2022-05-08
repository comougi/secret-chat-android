package com.ougi.serverinforepoimpl.data.network

import com.ougi.networkapi.data.NetworkService
import retrofit2.Call
import retrofit2.http.GET

interface ServerInfoNetworkService : NetworkService {

    @GET("publicKey")
    fun getPublicKey(): Call<String>

}