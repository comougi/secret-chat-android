package com.ougi.encryptionimpl.data.utils

import android.util.Base64
import com.ougi.encryptionapi.data.utils.HashUtils
import java.security.Key
import java.security.MessageDigest
import javax.crypto.Mac
import javax.inject.Inject
import kotlin.experimental.and

class HashUtilsImpl @Inject constructor() : HashUtils {

    override fun generateSha512Hash(from: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-512")
        messageDigest.update(getSalt(from))

        val bytes = messageDigest.digest(Base64.decode(from, Base64.DEFAULT))

        val sha512Hash = bytes.joinToString("") { byte ->
            ((byte and 0xff.toByte()) + 0x100)
                .toString(16)
                .substring(1)
        }
        return sha512Hash
    }

    override fun getSalt(from: String): ByteArray {
        return from.toCharArray().sorted().joinToString("").toByteArray()
    }

    override fun getHmac(string: String, key: Key): ByteArray {
        val bytes = string.encodeToByteArray()
        return getHmac(bytes, key)
    }

    override fun getHmac(bytes: ByteArray, key: Key): ByteArray {
        val mac = Mac.getInstance("HmacSHA512")
        mac.init(key)
        return mac.doFinal(bytes)
    }

}