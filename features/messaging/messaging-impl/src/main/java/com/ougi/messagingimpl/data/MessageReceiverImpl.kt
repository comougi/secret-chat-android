package com.ougi.messagingimpl.data

import android.util.Log
import com.ougi.messagingapi.data.MessageReceiver
import javax.inject.Inject

class MessageReceiverImpl @Inject constructor() : MessageReceiver {

    override fun receiveMessage(message: String) {
        Log.d("DATA", "MESSAGE $message")
    }

}