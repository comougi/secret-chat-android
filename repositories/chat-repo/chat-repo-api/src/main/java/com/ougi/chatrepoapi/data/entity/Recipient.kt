package com.ougi.chatrepoapi.data.entity

import kotlinx.serialization.Serializable

@Serializable
class Recipient(
    val id: String,
    val publicKey: String
)