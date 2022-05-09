package com.ougi.messagingapi.data

interface MessageReceiver {
    fun receiveMessage(message: String)
}