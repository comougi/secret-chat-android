package com.ougi.userrepoimpl.data.datastore

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
        encryptedDataStoreApi.write(USER_ID, userId)
    }

    override suspend fun readUserId(): String? {
        return encryptedDataStoreApi.read<String>(USER_ID).first()
    }

    companion object {
        private const val USER_ID_NAME = "UserId"
        private val USER_ID = stringPreferencesKey(USER_ID_NAME)
    }
}