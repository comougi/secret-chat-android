package com.ougi.messagerepoapi.data.entities

import com.ougi.encryptionapi.data.entity.AesEncryptedData
import kotlinx.serialization.Serializable

@Serializable
class EncryptedMessage(
    val encryptedAesKey: String,
    val encryptedMessage: AesEncryptedData
)