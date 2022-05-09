package com.ougi.userrepoapi.data.repository

import com.ougi.coreutils.utils.Result
import com.ougi.userrepoapi.data.entities.User

interface UserRepository {
    suspend fun register(): Result<User?>
}