package com.ougi.passwordscreenimpl.domain.usecase

import com.ougi.passwordscreenimpl.domain.repository.EnterPasswordRepository
import javax.inject.Inject

interface EnterPasswordUseCase {
    suspend fun hasPassword(): Boolean
    suspend fun isPasswordValid(password: String): Boolean
}

class EnterPasswordUseCaseImpl @Inject constructor(private val enterPasswordRepository: EnterPasswordRepository) :
    EnterPasswordUseCase {
    override suspend fun hasPassword(): Boolean {
        return enterPasswordRepository.hasPassword()
    }

    override suspend fun isPasswordValid(password: String): Boolean {
        val result = enterPasswordRepository.isPasswordValid(password)
        val isValid = result.first
        if (isValid) {
            val secretKey = result.second!!
            enterPasswordRepository.setAesKey(secretKey)
        }
        return isValid
    }

}