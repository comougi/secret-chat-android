package com.ougi.passwordscreenimpl.domain.repository

interface CreatePasswordRepository {
    suspend fun createPassword(password: String)
}