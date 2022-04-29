package com.ougi.encryptionimpl.data.utils

import android.util.Base64
import com.ougi.encryptionapi.data.utils.HashUtils
import java.security.Key
import java.security.MessageDigest
import javax.crypto.Mac
import kotlin.experimental.and

class HashUtilsImpl : HashUtils {

    override fun generateSha512Hash(from: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-512")
        messageDigest.update(getSalt(from))

        val bytes = messageDigest.digest(from.toByteArray())

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

    override fun getHmac(string: String, key: Key): String {
        val bytes = Base64.decode(string, Base64.DEFAULT)
        return getHmac(bytes, key)
    }

    override fun getHmac(bytes: ByteArray, key: Key): String {
        val mac = Mac.getInstance("HmacSHA512")
        mac.init(key)
        return Base64.encodeToString(mac.doFinal(bytes), Base64.DEFAULT)
    }

}