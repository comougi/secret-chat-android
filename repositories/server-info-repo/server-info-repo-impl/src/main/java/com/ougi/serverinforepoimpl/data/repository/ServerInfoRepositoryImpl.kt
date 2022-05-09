package com.ougi.serverinforepoimpl.data.repository

import com.ougi.coreutils.utils.Result
import com.ougi.serverinforepoapi.data.datastore.ServerInfoRepositoryDataStoreApi
import com.ougi.serverinforepoapi.data.entities.WebSocketInfo
import com.ougi.serverinforepoapi.data.network.ServerInfoRepositoryNetworkApi
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import javax.inject.Inject

class ServerInfoRepositoryImpl @Inject constructor(
    private val serverInfoRepositoryNetworkApi: ServerInfoRepositoryNetworkApi,
    private val serverInfoRepositoryDataStoreApi: ServerInfoRepositoryDataStoreApi,
    private val userRepositoryDataStoreApi: UserRepositoryDataStoreApi
) : ServerInfoRepository {

    override suspend fun getWebSocketInfo(): Result<WebSocketInfo?> {
        val userId = userRepositoryDataStoreApi.readUserId() ?: return Result.Error()
        val result = serverInfoRepositoryNetworkApi.getWebSocketInfo(userId)

        if (result is Result.Success) {
            val publicKey = result.data!!.publicKey
            if (serverInfoRepositoryDataStoreApi.readPublicKeyString() != publicKey)
                serverInfoRepositoryDataStoreApi.savePublicKey(publicKey)
        }

        return result
    }

}