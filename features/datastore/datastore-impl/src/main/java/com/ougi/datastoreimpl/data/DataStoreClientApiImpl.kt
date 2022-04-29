package com.ougi.datastoreimpl.data

import android.content.Context
import com.ougi.datastoreapi.data.DataStoreClientApi
import com.ougi.datastoreapi.data.dataStore
import javax.inject.Inject

class DataStoreClientApiImpl @Inject constructor(private val context: Context) :
    DataStoreClientApi {
    override val dataStore get() = context.dataStore
}