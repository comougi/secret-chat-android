package com.ougi.messagerepoapi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*

@[Serializable Entity(tableName = "system_messages")]
data class SystemMessage(
    @PrimaryKey
    override val id: String = UUID.randomUUID().toString(),
    override val senderId: String,
    override val chatId: String,
    override val recipientId: String? = null,
    override val type: Message.Type,
    override val data: Data,
    override var status: Message.Status? = Message.Status.SENDING
) : Message {

    @Serializable
    class Data(
        val type: Type,
        val data: String
    ) {

        @Serializable
        enum class Type {
            MESSAGE_DELIVERED,
            CHAT_ADDED
        }
    }

}