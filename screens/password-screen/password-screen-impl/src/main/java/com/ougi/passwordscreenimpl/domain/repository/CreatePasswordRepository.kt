package com.ougi.passwordscreenimpl.domain.repository

interface CreatePasswordRepository {
    suspend fun savePassword(password: String)
}