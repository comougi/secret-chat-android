package com.ougi.serverinforepoapi.data.datastore

import java.security.PublicKey

interface ServerInfoRepositoryDataStoreApi {
    suspend fun savePublicKey(publicKey: String)
    suspend fun readPublicKeyString(): String?
    suspend fun readPublicKey(): PublicKey?
}