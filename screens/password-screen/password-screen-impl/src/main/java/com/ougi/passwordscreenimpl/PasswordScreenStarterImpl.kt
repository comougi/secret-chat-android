package com.ougi.passwordscreenimpl

import android.content.Context
import android.content.Intent
import com.ougi.passwordscreenapi.data.PasswordScreenStarter
import com.ougi.passwordscreenimpl.presentation.view.PasswordScreenActivity
import javax.inject.Inject


class PasswordScreenStarterImpl @Inject constructor(private val context: Context) :
    PasswordScreenStarter {

    override fun start() {
        val intent = Intent(context, PasswordScreenActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(PasswordScreenActivity.IS_ON_START, false)
            }
        context.startActivity(intent)
    }


}