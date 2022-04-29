package com.ougi.datastoreapi.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ougi.corecommon.Config

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Config.DATASTORE_NAME)