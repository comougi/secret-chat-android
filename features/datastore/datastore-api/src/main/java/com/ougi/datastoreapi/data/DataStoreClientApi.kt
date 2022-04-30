package com.ougi.datastoreapi.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

interface DataStoreClientApi {
    val dataStore: DataStore<Preferences>
}

inline fun <reified K, reified V> DataStoreClientApi.read(key: Preferences.Key<K>): Flow<V?> {
    return dataStore.data.map { preferences ->
        preferences[key]?.let { value ->
            if (value is String && V::class.java != String::class.java) Json.decodeFromString(value)
            else value as V
        }
    }
}

suspend inline fun <reified K, reified V> DataStoreClientApi.write(
    key: Preferences.Key<K>,
    value: V
) {
    dataStore.edit { preferences ->
        if (value is K) preferences[key] = value
        else preferences[key] = Json.encodeToJsonElement(value).toString() as K
    }
}