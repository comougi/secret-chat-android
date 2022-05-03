package com.ougi.encryptionapi.data.utils

import java.security.KeyPair

interface KeyStorageUtils {
    suspend fun savePassword(password: String)
    suspend fun readPassword(): String
    suspend fun saveDhKeyPair(public: String, private: String)
    suspend fun saveRsaKeyPair(public: String, private: String)
    suspend fun readDhKeyPair(): KeyPair
    suspend fun readRsaKeyPair(): KeyPair
}