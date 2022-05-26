package com.ougi.secretchat

import android.app.Application
import com.ougi.messagingimpl.di.MessagingFeatureComponentHolder
import com.ougi.workmanagerinitializer.di.WorkManagerInititalizerComponentHolder

class App : Application() {

    override fun onCreate() {
        Injector.injectAll(applicationContext)
        super.onCreate()
        WorkManagerInititalizerComponentHolder.getInstance()
            .workManagerInitializer.initialize()

        application = this
    }

    override fun onTrimMemory(level: Int) {
        MessagingFeatureComponentHolder.getInstance().messagingFeatureClientApi
            .startMessagingWork(false)
        super.onTrimMemory(level)
    }

    companion object {
        private var application: App? = null
    }
}