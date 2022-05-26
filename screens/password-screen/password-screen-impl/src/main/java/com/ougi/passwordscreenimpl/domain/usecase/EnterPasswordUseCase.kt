package com.ougi.passwordscreenimpl.domain.usecase

import com.ougi.passwordscreenimpl.domain.repository.PasswordRepository
import javax.inject.Inject

interface EnterPasswordUseCase {
    suspend fun hasPassword(): Boolean
    suspend fun isPasswordValid(password: String): Boolean
}

class EnterPasswordUseCaseImpl @Inject constructor(private val passwordRepository: PasswordRepository) :
    EnterPasswordUseCase {
    override suspend fun hasPassword(): Boolean {
        return passwordRepository.isHasPassword()
    }

    override suspend fun isPasswordValid(password: String): Boolean {
        val result = passwordRepository.isPasswordValid(password)
        val isValid = result.first
        if (isValid) {
            val secretKey = result.second!!
            passwordRepository.setAesKey(secretKey)
        }
        return isValid
    }

}