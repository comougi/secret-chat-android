package com.ougi.chatrepoapi.data.entity

import kotlinx.serialization.Serializable

@Serializable
class GroupChat(
    override val id: String,
    override val chatName: String,
    val recipients: List<Recipient>,
    override val lastMessage: String
) : Chat