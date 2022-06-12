package com.ougi.messagerepoapi.data.entities

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable


interface Message {
    val id: String
    val senderId: String
    val chatId: String
    val recipientId: String?
    val type: Type
    var data: String
    var status: Status?
    val date: Instant
        get() = Clock.System.now()

    @Serializable
    enum class Type {
        PERSONAL, SYSTEM
    }

    @Serializable
    enum class Status {
        SENDING, SENT, DELIVERED, FAIL
    }
}