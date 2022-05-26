package com.ougi.secretchat.presentation

import android.os.Bundle
import android.widget.Toast
import com.ougi.corecommon.base.view.BaseActivity
import com.ougi.messagingimpl.di.MessagingFeatureComponentHolder
import com.ougi.secretchat.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MessagingFeatureComponentHolder.getInstance().messagingFeatureClientApi
            .startMessagingWork(true)
        Toast.makeText(this, "MAS", Toast.LENGTH_LONG).show()

    }

}