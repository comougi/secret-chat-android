package com.ougi.serverinforepoapi.data.entities

import kotlinx.serialization.Serializable

@Serializable
class WebSocketInfo(
    val publicKey: String,
    val wsLink: String
)