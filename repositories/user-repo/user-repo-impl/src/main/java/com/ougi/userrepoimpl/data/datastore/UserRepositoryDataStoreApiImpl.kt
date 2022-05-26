package com.ougi.userrepoimpl.data.datastore

import android.util.Base64
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ougi.encryptionapi.data.EncryptedDataStoreApi
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryDataStoreApiImpl @Inject constructor(
    private val encryptedDataStoreApi: EncryptedDataStoreApi
) :
    UserRepositoryDataStoreApi {

    override suspend fun saveUserId(userId: String) {
        val userIdBytes = Base64.decode(userId, Base64.DEFAULT)
        val userIdString = userIdBytes.decodeToString()
        encryptedDataStoreApi.write(USER_ID, userIdString, false)
    }

    override suspend fun readUserId(): String? {
        return encryptedDataStoreApi.read<String>(USER_ID, false).first()
    }

    companion object {
        private const val USER_ID_NAME = "UserId"
        private val USER_ID = stringPreferencesKey(USER_ID_NAME)
    }
}