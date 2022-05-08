package com.ougi.serverinforepoapi.data

import java.security.PublicKey

interface ServerInfoDataStoreApi {
    suspend fun savePublicKey(publicKey: String)
    suspend fun readPublicKeyString(): String?
    suspend fun readPublicKey(): PublicKey?
}