package com.ougi.encryptionapi.data.entity

import android.util.Base64
import kotlinx.serialization.Serializable

@Serializable
class AesEncryptedData(
    val encryptedData: String,
    val hash: String,
    val iv: String
) {

    fun hashBytes(): ByteArray {
        return decodeToBase64ByteArray(hash)
    }

    fun ivBytes(): ByteArray {
        return decodeToBase64ByteArray(iv)
    }

    private fun decodeToBase64ByteArray(data: String): ByteArray {
        return Base64.decode(data, Base64.NO_WRAP)
    }

    class Builder {
        private var encryptedData: String? = null
        private var hash: String? = null
        private var iv: String? = null

        fun hash(hashBytes: ByteArray): Builder {
            hash = encodeToBase64String(hashBytes)
            return this
        }

        fun hash(hashString: String): Builder {
            hash = hashString
            return this
        }

        fun iv(ivBytes: ByteArray): Builder {
            iv = encodeToBase64String(ivBytes)
            return this
        }

        fun iv(ivString: String): Builder {
            iv = ivString
            return this
        }

        fun encryptedData(encryptedDataBytes: ByteArray): Builder {
            encryptedData = encodeToBase64String(encryptedDataBytes)
            return this
        }

        fun encryptedData(encryptedDataString: String): Builder {
            encryptedData = encryptedDataString
            return this
        }

        fun build(): AesEncryptedData {
            return AesEncryptedData(
                encryptedData = encryptedData!!,
                hash = hash!!,
                iv = iv!!
            )
        }

        private fun encodeToBase64String(bytes: ByteArray): String {
            return Base64.encodeToString(bytes, Base64.NO_WRAP)
        }
    }

}