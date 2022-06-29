package com.ougi.passwordscreenimpl.domain.repository

import javax.crypto.SecretKey

interface PasswordRepository {
    suspend fun savePassword(password: String)
    suspend fun isHasPassword(): Boolean
    suspend fun setAesKey(secretKey: SecretKey)
    suspend fun isPasswordValid(password: String): Pair<Boolean, SecretKey?>
}