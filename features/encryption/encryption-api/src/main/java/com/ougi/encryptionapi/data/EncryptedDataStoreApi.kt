package com.ougi.encryptionapi.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ougi.encryptionapi.data.entity.AesEncryptedData
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.crypto.SecretKey

abstract class EncryptedDataStoreApi {

    abstract val encryptionUtils: EncryptionUtils
    abstract val secretKey: SecretKey?
    abstract val dataStore: DataStore<Preferences>

    inline fun <reified V> read(
        key: Preferences.Key<String>,
        decrypt: Boolean = true
    ): Flow<V?> {
        return dataStore.data.map { preferences ->
            preferences[key]?.let { value ->
                val data = if (decrypt) {
                    val aesEncryptedData = Json.decodeFromString<AesEncryptedData>(value)
                    encryptionUtils.decryptViaSecretKey(aesEncryptedData, secretKey!!).first
                } else
                    value
                if (V::class.java != String::class.java) Json.decodeFromString(data)
                else data as V
            }
        }
    }

    suspend inline fun <reified V> write(
        key: Preferences.Key<String>,
        value: V,
        encrypt: Boolean = true
    ) {
        dataStore.edit { preferences ->
            val data = if (value is String) value
            else Json.encodeToString(value)
            preferences[key] =
                if (encrypt) {
                    val encryptedData = encryptionUtils.encryptViaSecretKey(data, secretKey!!)
                    Json.encodeToString(encryptedData)
                } else data
        }
    }
}

