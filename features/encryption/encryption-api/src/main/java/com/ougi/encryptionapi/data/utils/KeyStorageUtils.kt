package com.ougi.encryptionapi.data.utils

import java.security.KeyPair
import javax.crypto.SecretKey

interface KeyStorageUtils {
    suspend fun savePassword(password: String, secretKey: SecretKey)
    suspend fun hasPassword(): Boolean
    suspend fun readPassword(secretKey: SecretKey): String?
    suspend fun saveDhKeyPair(public: String, private: String)
    suspend fun saveRsaKeyPair(public: String, private: String)
    suspend fun readDhKeyPair(): KeyPair
    suspend fun readRsaKeyPair(): KeyPair
}