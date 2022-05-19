package com.ougi.encryptionapi.data.entity

class DividedData(
    val data: String,
    val iv: ByteArray,
    val hash: ByteArray
)