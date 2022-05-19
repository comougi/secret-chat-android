package com.ougi.passwordscreenimpl.domain.usecase

import com.ougi.coreutils.utils.Result
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import com.ougi.userrepoapi.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface UserRegistrationUseCase {
    val idResultStateFlow: StateFlow<Result<String?>>
    suspend fun isRegistered(): Boolean
    suspend fun registerUser()
}

class UserRegistrationUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val userRepositoryDataStoreApi: UserRepositoryDataStoreApi
) :
    UserRegistrationUseCase {

    override val idResultStateFlow: MutableStateFlow<Result<String?>> =
        MutableStateFlow(Result.Loading())

    override suspend fun isRegistered(): Boolean {
        return userRepositoryDataStoreApi.readUserId() != null
    }

    override suspend fun registerUser() {
        idResultStateFlow.value = Result.Loading()
        idResultStateFlow.value = userRepository.register()
    }

}