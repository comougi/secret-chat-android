package com.ougi.passwordscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.passwordscreenimpl.domain.usecase.CheckHasPasswordUseCase
import javax.inject.Inject

interface PasswordScreenActivityViewModel {
    suspend fun isHasPassword(): Boolean
    interface Factory : ViewModelProvider.Factory
}

class PasswordScreenActivityViewModelImpl(private val checkHasPasswordUseCase: CheckHasPasswordUseCase) :
    ViewModel(), PasswordScreenActivityViewModel {

    override suspend fun isHasPassword(): Boolean {
        return checkHasPasswordUseCase.isHasPassword()
    }

    class Factory @Inject constructor(private val checkHasPasswordUseCase: CheckHasPasswordUseCase) :
        PasswordScreenActivityViewModel.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PasswordScreenActivityViewModelImpl(checkHasPasswordUseCase) as T
        }

    }
}