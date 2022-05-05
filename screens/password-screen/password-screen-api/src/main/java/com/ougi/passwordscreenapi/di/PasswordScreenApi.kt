package com.ougi.passwordscreenapi.di

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.passwordscreenapi.data.PasswordScreenStarter

interface PasswordScreenApi : BaseFeatureApi {
    val passwordScreenStarter: PasswordScreenStarter
}