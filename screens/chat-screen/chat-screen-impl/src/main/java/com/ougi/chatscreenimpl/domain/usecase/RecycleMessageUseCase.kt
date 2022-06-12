package com.ougi.chatscreenimpl.domain.usecase

import com.ougi.userrepoapi.data.repository.UserRepository
import javax.inject.Inject

interface RecycleMessageUseCase {
    suspend fun getUserId(): String
}

class RecycleMessageUseCaseImpl @Inject constructor(private val userRepository: UserRepository) :
    RecycleMessageUseCase {

    override suspend fun getUserId(): String {
        return userRepository.getUserId()
    }

}