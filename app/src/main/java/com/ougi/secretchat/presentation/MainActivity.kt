package com.ougi.secretchat.presentation

import com.ougi.corecommon.base.view.BaseActivity
import com.ougi.messagingimpl.di.MessagingFeatureComponentHolder
import com.ougi.secretchat.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onStart() {
        super.onStart()
        MessagingFeatureComponentHolder.getInstance().messagingFeatureClientApi
            .startMessagingWork(true)
    }

}