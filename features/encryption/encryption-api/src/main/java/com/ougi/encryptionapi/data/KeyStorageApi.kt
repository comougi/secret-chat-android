package com.ougi.encryptionapi.data

import java.security.KeyPair
import javax.crypto.SecretKey

interface KeyStorageApi {
    suspend fun savePassword(password: String)
    suspend fun hasPassword(): Boolean
    suspend fun readPassword(): String?
    suspend fun saveDhKeyPair(public: String, private: String)
    suspend fun saveRsaKeyPair(public: String, private: String)
    suspend fun readDhKeyPair(): KeyPair
    suspend fun readRsaKeyPair(): KeyPair
    suspend fun readDHAesKey(): SecretKey
}