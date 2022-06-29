package com.ougi.chatlistscreenimpl.domain.usecase

import com.ougi.userrepoapi.data.repository.UserRepository
import javax.inject.Inject

interface GetUserInfoUseCase {
    suspend fun getUserId(): String
}

class GetUserInfoUseCaseImpl @Inject constructor(private val userRepository: UserRepository) :
    GetUserInfoUseCase {

    override suspend fun getUserId(): String {
        return userRepository.getUserId()
    }

}