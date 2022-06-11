package com.ougi.messagerepoapi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*

@[Serializable Entity(tableName = "personal_messages")]
data class PersonalMessage(
    @PrimaryKey
    override val id: String = UUID.randomUUID().toString(),
    override val senderId: String,
    override val chatId: String,
    override val recipientId: String? = null,
    override val type: Message.Type,
    override val data: String,
    override var status: Message.Status? = Message.Status.SENDING,
) : Message

