package com.ougi.chatrepoapi.data.entity

import kotlinx.serialization.Serializable

@Serializable
class SingleChat(
    override val id: String,
    override val chatName: String,
    val recipient: Recipient,
    override val lastMessage: String,
    val status: Int = ChatStatus.OFFLINE
) : Chat