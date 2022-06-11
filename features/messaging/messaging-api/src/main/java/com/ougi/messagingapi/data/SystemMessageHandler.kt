package com.ougi.messagingapi.data

import com.ougi.messagerepoapi.data.entities.SystemMessage

interface SystemMessageHandler {
    suspend fun handleAll()
    suspend fun handle(message: SystemMessage)
}