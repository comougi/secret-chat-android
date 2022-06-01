package com.ougi.userrepoapi.data.entities

import kotlinx.serialization.Serializable

@Serializable
class User(val id: String, val publicKey: String)