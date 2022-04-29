package com.ougi.encryptionapi.data.entity

import kotlinx.serialization.Serializable

@Serializable
class AesKey(
    val key: String,
    val iv: ByteArray
)