package com.ougi.passwordscreenimpl.domain.repository

import javax.crypto.SecretKey

interface EnterPasswordRepository {
    suspend fun hasPassword(): Boolean
    suspend fun setAesKey(secretKey: SecretKey)
    suspend fun isPasswordValid(password: String): Pair<Boolean, SecretKey?>
}