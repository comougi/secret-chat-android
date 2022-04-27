package com.ougi.secretchat

import android.app.Application

class App : Application() {

    override fun onCreate() {
        Injector.injectAll(applicationContext)
        super.onCreate()
        application = this
    }


    companion object {
        private var application: App? = null
    }
}