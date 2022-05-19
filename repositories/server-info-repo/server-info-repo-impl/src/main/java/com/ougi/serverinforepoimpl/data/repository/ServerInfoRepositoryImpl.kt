package com.ougi.serverinforepoimpl.data.repository

import com.ougi.coreutils.utils.Result
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.serverinforepoapi.data.network.ServerInfoRepositoryNetworkApi
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import javax.inject.Inject

class ServerInfoRepositoryImpl @Inject constructor(
    private val serverInfoRepositoryNetworkApi: ServerInfoRepositoryNetworkApi,
    private val userRepositoryDataStoreApi: UserRepositoryDataStoreApi,
    private val encryptionClientApi: EncryptionClientApi
) : ServerInfoRepository {

    override suspend fun getWebSocketConnectionLink(): Result<String?> {
        val userId = userRepositoryDataStoreApi.readUserId() ?: return Result.Error()
        val webSocketLinkResult =
            serverInfoRepositoryNetworkApi.getWebSocketConnectionLink(userId)
        if (webSocketLinkResult is Result.Success) {
            val encryptedWebSocketLink = webSocketLinkResult.data!!
            val decryptedWebSocketLink =
                encryptionClientApi.decryptViaDHAesKey(encryptedWebSocketLink)
            if (decryptedWebSocketLink.second)
                return Result.Success(decryptedWebSocketLink.first)
        }
        return Result.Error()
    }

}