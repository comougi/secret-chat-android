package com.ougi.userrepoimpl.data.network

import com.ougi.encryptionapi.data.entity.AesEncryptedData
import com.ougi.networkapi.data.NetworkService
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRepositoryNetworkService : NetworkService {

    @POST("register")
    fun register(@Body publicKeys: Map<String, String>): Call<AesEncryptedData>

    @POST("isUserRegistered")
    fun isUserRegistered(
        @Query("userId") userId: String,
        @Query("isRegisteredId") isRegisteredId: String,
        @Body encryptedUserId: String
    ): Call<AesEncryptedData>
}