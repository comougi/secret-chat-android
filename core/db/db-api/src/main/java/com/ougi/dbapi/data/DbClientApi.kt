package com.ougi.dbapi.data

interface DbClientApi {
    fun provideDatabase(databaseClass: Class<out BaseDatabase>): BaseDatabase
}