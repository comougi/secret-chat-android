package com.ougi.messagerepoapi.data.entities

import kotlinx.serialization.Serializable

interface Message {
    val id: String
    val senderId: String
    val chatId: String
    val recipientId: String?
    val type: Type
    val data: Any
    var status: Status?

    @Serializable
    enum class Type {
        PERSONAL, SYSTEM
    }

    @Serializable
    enum class Status {
        SENDING, SENT, DELIVERED, FAIL
    }
}