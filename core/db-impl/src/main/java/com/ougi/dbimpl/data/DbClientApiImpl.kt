package com.ougi.dbimpl.data

import android.content.Context
import androidx.room.Room
import com.ougi.corecommon.Config
import com.ougi.dbapi.data.BaseDatabase
import com.ougi.dbapi.data.DbClientApi
import javax.inject.Inject

internal class DbClientApiImpl @Inject constructor(private val context: Context) : DbClientApi {
    override fun provideDatabase(): BaseDatabase {
        return Room.databaseBuilder(context, Database::class.java, Config.APP_DATABASE_NAME)
            .build()
    }
}