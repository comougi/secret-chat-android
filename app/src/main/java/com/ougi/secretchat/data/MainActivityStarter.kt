package com.ougi.secretchat.data

import android.content.Context
import android.content.Intent
import com.ougi.corecommon.base.ActivityStarter
import com.ougi.secretchat.presentation.MainActivity
import javax.inject.Inject

interface MainActivityStarter : ActivityStarter

class MainActivityStarterImpl @Inject constructor(private val context: Context) :
    MainActivityStarter {

    override fun start() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

}