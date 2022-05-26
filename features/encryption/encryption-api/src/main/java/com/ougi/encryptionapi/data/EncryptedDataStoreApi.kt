package com.ougi.encryptionapi.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
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
                val data = if (decrypt)
                    encryptionUtils.decryptViaSecretKeyDivided(value, secretKey!!).first
                else
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
            else Json.encodeToJsonElement(value).toString()
            preferences[key] =
                if (encrypt) encryptionUtils.encryptViaSecretKeySeparated(data, secretKey!!)
                else data
        }
    }
}

