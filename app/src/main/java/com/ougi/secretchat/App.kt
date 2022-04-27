package com.ougi.secretchat

import android.app.Application
import com.ougi.workmanagerinitializer.di.WorkManagerInititalizerComponentHolder

class App : Application() {

    override fun onCreate() {
        Injector.injectAll(applicationContext)
        super.onCreate()
        WorkManagerInititalizerComponentHolder.getInstance()
            .workManagerInitializer.initialize()
        application = this
    }


    companion object {
        private var application: App? = null
    }
}