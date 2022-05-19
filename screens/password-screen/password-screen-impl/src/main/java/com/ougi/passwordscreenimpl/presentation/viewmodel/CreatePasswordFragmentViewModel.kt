package com.ougi.passwordscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.passwordscreenimpl.domain.usecase.CreatePasswordUseCase
import javax.inject.Inject

interface CreatePasswordFragmentViewModel {
   suspend fun savePassword(password: String)
    interface Factory : ViewModelProvider.Factory
}

class CreatePasswordFragmentViewModelImpl(private val createPasswordUseCase: CreatePasswordUseCase) :
    ViewModel(), CreatePasswordFragmentViewModel {


    override suspend fun savePassword(password: String) {
        // viewModelScope.launch(Dispatchers.IO) {
        createPasswordUseCase.savePassword(password)
        //}
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val createPasswordUseCase: CreatePasswordUseCase) :
        CreatePasswordFragmentViewModel.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CreatePasswordFragmentViewModelImpl(createPasswordUseCase) as T
        }
    }
}