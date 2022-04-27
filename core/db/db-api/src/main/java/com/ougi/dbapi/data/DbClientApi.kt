package com.ougi.dbapi.data

interface DbClientApi {
    fun provideDatabase(): BaseDatabase
}