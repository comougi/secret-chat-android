package com.ougi.serverinforepoimpl.data.repository

import android.util.Base64
import android.util.Log
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

    override suspend fun getMessagingWebSocketConnectionLink(): Result<String?> {
        val userId = userRepositoryDataStoreApi.readUserId() ?: return Result.Error()
        val userIdEncrypted = encryptionClientApi.encryptViaDHAesKey(userId)
        val webSocketLinkResult = serverInfoRepositoryNetworkApi
            .getMessagingWebSocketConnectionLink(userId, userIdEncrypted)

        if (webSocketLinkResult is Result.Success) {
            val encryptedWebSocketLink = webSocketLinkResult.data!!
            val decryptedWebSocketLink =
                encryptionClientApi.decryptViaDHAesKey(encryptedWebSocketLink)
            val decryptedWebSocketLinkBytes =
                Base64.decode(decryptedWebSocketLink.first, Base64.DEFAULT)
            val decryptedWebSocketLinkDecoded = decryptedWebSocketLinkBytes.decodeToString()

            if (decryptedWebSocketLink.second) {
                Log.d("DATA", "M WS LINK  $decryptedWebSocketLinkDecoded")
                return Result.Success(decryptedWebSocketLinkDecoded)
            }
        }
        return webSocketLinkResult
    }

    override suspend fun getPushWebSocketConnectionLink(): Result<String?> {
        val userId = userRepositoryDataStoreApi.readUserId() ?: return Result.Error()
        val webSocketLinkResult =
            serverInfoRepositoryNetworkApi.getPushWebSocketConnectionLink(userId)

        return if (webSocketLinkResult is Result.Success) {
            Log.d("DATA", "P WS LINK  ${webSocketLinkResult.data!!}")
            Result.Success(webSocketLinkResult.data!!)
        } else webSocketLinkResult
    }

}