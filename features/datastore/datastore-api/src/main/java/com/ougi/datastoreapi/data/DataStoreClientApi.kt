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

inline fun <reified T> DataStoreClientApi.read(key: Preferences.Key<String>): Flow<T?> {
    return dataStore.data.map { preferences ->
        preferences[key]?.let { Json.decodeFromString(it) }
    }
}

suspend inline fun <reified T> DataStoreClientApi.write(key: Preferences.Key<String>, value: T) {
    dataStore.edit { preferences ->
        preferences[key] = Json.encodeToJsonElement(value).toString()
    }
}