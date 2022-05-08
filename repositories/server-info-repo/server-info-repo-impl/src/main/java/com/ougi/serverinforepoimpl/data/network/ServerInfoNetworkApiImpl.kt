package com.ougi.serverinforepoimpl.data.network

import com.ougi.coreutils.utils.Result
import com.ougi.serverinforepoapi.data.ServerInfoNetworkApi
import retrofit2.awaitResponse
import javax.inject.Inject

class ServerInfoNetworkApiImpl @Inject constructor(
    private val serverInfoNetworkService: ServerInfoNetworkService
) : ServerInfoNetworkApi {

    override suspend fun getPublicKeyFromServer(): Result<String?> {
        return try {
            serverInfoNetworkService.getPublicKey().awaitResponse().let { response ->
                if (response.isSuccessful) Result.Success(response.body())
                else Result.Error()
            }
        } catch (e: Exception) {
            Result.Error()
        }
    }
}