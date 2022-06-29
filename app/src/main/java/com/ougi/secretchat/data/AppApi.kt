package com.ougi.secretchat.data

import com.ougi.corecommon.base.di.BaseFeatureApi

interface AppApi : BaseFeatureApi {
    val mainActivityStarter: MainActivityStarter
}