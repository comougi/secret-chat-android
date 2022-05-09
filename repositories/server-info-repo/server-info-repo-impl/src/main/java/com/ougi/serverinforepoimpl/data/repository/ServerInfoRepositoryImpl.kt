package com.ougi.serverinforepoimpl.data.repository

import com.ougi.coreutils.utils.Result
import com.ougi.serverinforepoapi.data.datastore.ServerInfoRepositoryDataStoreApi
import com.ougi.serverinforepoapi.data.entities.WebSocketInfo
import com.ougi.serverinforepoapi.data.network.ServerInfoRepositoryNetworkApi
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository
import javax.inject.Inject

class ServerInfoRepositoryImpl @Inject constructor(
    private val serverInfoRepositoryNetworkApi: ServerInfoRepositoryNetworkApi,
    private val serverInfoRepositoryDataStoreApi: ServerInfoRepositoryDataStoreApi
) : ServerInfoRepository {

    override suspend fun getWebSocketInfo(userId: String): Result<WebSocketInfo?> {
        val result = serverInfoRepositoryNetworkApi.getWebSocketInfo(userId)

        if (result is Result.Success) {
            val publicKey = result.data!!.publicKey
            if (serverInfoRepositoryDataStoreApi.readPublicKeyString() != publicKey)
                serverInfoRepositoryDataStoreApi.savePublicKey(publicKey)
        }

        return result
    }

}