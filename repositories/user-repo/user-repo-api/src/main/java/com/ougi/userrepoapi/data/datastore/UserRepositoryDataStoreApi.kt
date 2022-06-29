package com.ougi.userrepoapi.data.datastore

interface UserRepositoryDataStoreApi {
    suspend fun saveUserId(userId: String)
    suspend fun readUserId(): String?
}