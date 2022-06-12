package com.ougi.messagerepoapi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
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
    override var data: String,
    override var status: Message.Status? = Message.Status.SENDING,
    override val date: Instant = Clock.System.now()
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