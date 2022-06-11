package com.ougi.messagingapi.data

interface MessageReceiver {
    suspend fun receiveMessage(messageText: String)
}