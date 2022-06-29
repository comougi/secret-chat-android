package com.ougi.passwordscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ougi.coreutils.utils.Result
import com.ougi.passwordscreenimpl.domain.usecase.UserRegistrationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface UserRegistrationViewModel {
    val idResultStateFlow: StateFlow<Result<String?>>
    suspend fun isRegistered(): Boolean
    fun registerUser()
    interface Factory : ViewModelProvider.Factory {
        fun create(): UserRegistrationViewModel
    }
}

class UserRegistrationViewModelImpl(private val userRegistrationUseCase: UserRegistrationUseCase) :
    ViewModel(), UserRegistrationViewModel {

    override val idResultStateFlow: StateFlow<Result<String?>> =
        userRegistrationUseCase.idResultStateFlow

    override suspend fun isRegistered(): Boolean {
        return userRegistrationUseCase.isRegistered()
    }

    override fun registerUser() {
        viewModelScope.launch(Dispatchers.IO) {
            userRegistrationUseCase.registerUser()
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val userRegistrationUseCase: UserRegistrationUseCase) :
        UserRegistrationViewModel.Factory {
        override fun create(): UserRegistrationViewModel {
            return create(UserRegistrationViewModelImpl::class.java)
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserRegistrationViewModelImpl(userRegistrationUseCase) as T
        }
    }
}