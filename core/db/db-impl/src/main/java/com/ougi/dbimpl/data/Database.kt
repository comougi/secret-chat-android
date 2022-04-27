package com.ougi.dbimpl.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ougi.dbapi.data.BaseDatabase

@Database(entities = [], version = 1, exportSchema = false)
//@TypeConverters(CoreDbTypeConverter::class)
internal abstract class Database : RoomDatabase(), BaseDatabase