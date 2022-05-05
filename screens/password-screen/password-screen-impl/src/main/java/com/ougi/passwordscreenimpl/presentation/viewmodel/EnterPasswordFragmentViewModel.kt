package com.ougi.passwordscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.passwordscreenimpl.domain.usecase.EnterPasswordUseCase
import javax.inject.Inject

interface EnterPasswordFragmentViewModel {
    suspend fun hasPassword(): Boolean
    suspend fun isPasswordValid(password: String): Boolean
    interface Factory : ViewModelProvider.Factory
}

class EnterPasswordFragmentViewModelImpl(private val enterPasswordUseCase: EnterPasswordUseCase) :
    ViewModel(), EnterPasswordFragmentViewModel {

    override suspend fun hasPassword(): Boolean {
        return enterPasswordUseCase.hasPassword()
    }

    override suspend fun isPasswordValid(password: String): Boolean {
        return enterPasswordUseCase.isPasswordValid(password)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val enterPasswordUseCase: EnterPasswordUseCase) :
        EnterPasswordFragmentViewModel.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EnterPasswordFragmentViewModelImpl(enterPasswordUseCase) as T
        }

    }
}