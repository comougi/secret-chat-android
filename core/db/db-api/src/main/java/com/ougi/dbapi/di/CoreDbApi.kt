package com.ougi.dbapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.dbapi.data.DbClientApi

interface CoreDbApi : BaseFeatureApi {
    val dbClientApi: DbClientApi
}