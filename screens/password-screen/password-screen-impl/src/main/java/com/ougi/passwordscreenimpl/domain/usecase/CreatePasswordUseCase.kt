package com.ougi.passwordscreenimpl.domain.usecase

import com.ougi.passwordscreenimpl.domain.repository.PasswordRepository
import javax.inject.Inject

interface CreatePasswordUseCase {
    suspend fun savePassword(password: String)
}

class CreatePasswordUseCaseImpl @Inject constructor(
    private val passwordRepository: PasswordRepository
) : CreatePasswordUseCase {

    override suspend fun savePassword(password: String) {
        passwordRepository.savePassword(password)
    }
}