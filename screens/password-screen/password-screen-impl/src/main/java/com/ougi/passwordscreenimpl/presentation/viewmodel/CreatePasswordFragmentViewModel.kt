package com.ougi.passwordscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ougi.passwordscreenimpl.domain.usecase.CreatePasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface CreatePasswordFragmentViewModel {
    fun savePassword(password: String)
    interface Factory : ViewModelProvider.Factory
}

class CreatePasswordFragmentViewModelImpl(private val createPasswordUseCase: CreatePasswordUseCase) :
    ViewModel(), CreatePasswordFragmentViewModel {

    override fun savePassword(password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            createPasswordUseCase.savePassword(password)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val createPasswordUseCase: CreatePasswordUseCase) :
        CreatePasswordFragmentViewModel.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CreatePasswordFragmentViewModelImpl(createPasswordUseCase) as T
        }
    }
}