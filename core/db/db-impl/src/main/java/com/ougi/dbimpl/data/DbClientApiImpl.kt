package com.ougi.dbimpl.data

import android.content.Context
import androidx.room.Room
import com.ougi.dbapi.data.BaseDatabase
import com.ougi.dbapi.data.DbClientApi
import javax.inject.Inject

internal class DbClientApiImpl @Inject constructor(private val context: Context) : DbClientApi {
    override fun provideDatabase(databaseClass: Class<out BaseDatabase>): BaseDatabase {
        return Room.databaseBuilder(context, databaseClass, databaseClass.name)
            .fallbackToDestructiveMigration()
            .build()
    }
}