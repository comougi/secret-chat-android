package com.ougi.passwordscreenimpl.domain.usecase

import com.ougi.passwordscreenimpl.domain.repository.PasswordRepository
import javax.inject.Inject

interface CheckHasPasswordUseCase {
    suspend fun isHasPassword(): Boolean
}

class CheckHasPasswordUseCaseImpl @Inject constructor(private val passwordRepository: PasswordRepository) :
    CheckHasPasswordUseCase {

    override suspend fun isHasPassword(): Boolean {
        return passwordRepository.isHasPassword()
    }

}