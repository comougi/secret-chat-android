package com.ougi.userrepoimpl.data.network

import com.ougi.networkapi.data.NetworkService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRepositoryNetworkService : NetworkService {

    @POST("register")
    fun register(@Body publicKey: String): Call<String>

}