package com.ougi.serverinforepoimpl.data.datastore

import android.util.Base64
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.datastoreapi.data.read
import com.ougi.datastoreapi.data.write
import com.ougi.serverinforepoapi.data.datastore.ServerInfoRepositoryDataStoreApi
import kotlinx.coroutines.flow.first
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.inject.Inject

class ServerInfoRepositoryDataStoreApiImpl @Inject constructor(private val dataStoreClientApi: DataStoreClientApi) :
    ServerInfoRepositoryDataStoreApi {

    override suspend fun savePublicKey(publicKey: String) {
        dataStoreClientApi.write(SERVER_PUBLIC_KEY, publicKey)
    }

    override suspend fun readPublicKeyString(): String? {
        val publicKeyFlow = dataStoreClientApi.read<String, String>(SERVER_PUBLIC_KEY)
        return publicKeyFlow.first()
    }

    override suspend fun readPublicKey(): PublicKey? {
        val publicKeyStr = readPublicKeyString()
        val publicKeyBytes = Base64.decode(publicKeyStr, Base64.DEFAULT)
        val keyFactory = KeyFactory.getInstance("DH")
        return keyFactory.generatePublic(X509EncodedKeySpec(publicKeyBytes))
    }

    companion object {
        private const val SERVER_PUBLIC_KEY_NAME = "ServerPublicKey"
        val SERVER_PUBLIC_KEY = stringPreferencesKey(SERVER_PUBLIC_KEY_NAME)
    }
}