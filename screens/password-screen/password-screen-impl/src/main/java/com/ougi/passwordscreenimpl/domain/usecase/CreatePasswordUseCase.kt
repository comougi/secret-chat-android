package com.ougi.passwordscreenimpl.domain.usecase

import com.ougi.passwordscreenimpl.domain.repository.CreatePasswordRepository
import javax.inject.Inject

interface CreatePasswordUseCase {
    suspend fun savePassword(password: String)
}

class CreatePasswordUseCaseImpl @Inject constructor(
    private val createPasswordRepository: CreatePasswordRepository
) : CreatePasswordUseCase {

    override suspend fun savePassword(password: String) {
        createPasswordRepository.savePassword(password)
    }
}