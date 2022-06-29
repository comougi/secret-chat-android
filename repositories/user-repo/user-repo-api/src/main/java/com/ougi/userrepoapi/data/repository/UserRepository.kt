package com.ougi.userrepoapi.data.repository

import com.ougi.coreutils.utils.Result

interface UserRepository {
    suspend fun register(): Result<String?>
    suspend fun isUserRegistered(isRegisteredId: String): Result<Boolean?>
    suspend fun getUserId(): String
}