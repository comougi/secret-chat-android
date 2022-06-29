package com.ougi.chatscreenapi.di

import com.ougi.chatscreenapi.data.ChatScreenStarter
import com.ougi.corecommon.base.di.BaseFeatureApi

interface ChatScreenApi : BaseFeatureApi {
    val chatScreenStarterFactory: ChatScreenStarter.Factory
}