package com.ougi.secretchat.presentation

import androidx.lifecycle.lifecycleScope
import com.ougi.corecommon.base.view.BaseActivity
import com.ougi.messagingimpl.di.MessagingFeatureComponentHolder
import com.ougi.secretchat.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onStart() {
        super.onStart()

        startMessagingWebSocket()
        defineSystemMessages()
    }

    private fun startMessagingWebSocket() {
        MessagingFeatureComponentHolder.getInstance().messagingFeatureClientApi
            .startMessagingWork(true)
    }

    private fun defineSystemMessages() = lifecycleScope.launch {
        MessagingFeatureComponentHolder.getInstance().systemMessageHandler.handleAll()
    }

}