package com.ougi.chatlistscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ougi.chatlistscreenimpl.domain.usecase.CreateChatUseCase
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface CreateChatDialogFragmentViewModel {
    val hasUserResultStateFlow: StateFlow<Result<Boolean?>>
    val createChatResultStateFlow: StateFlow<Result<Chat?>>
    fun isUserRegistered(id: String)
    fun createChat(userIds: List<String>)
    interface Factory : ViewModelProvider.Factory
}

class CreateChatDialogFragmentViewModelImpl(private val createChatUseCase: CreateChatUseCase) :
    ViewModel(), CreateChatDialogFragmentViewModel {

    override val hasUserResultStateFlow: StateFlow<Result<Boolean?>> =
        createChatUseCase.hasUserResultStateFlow

    override val createChatResultStateFlow: StateFlow<Result<Chat?>> =
        createChatUseCase.createChatResultStateFlow


    override fun isUserRegistered(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            createChatUseCase.isUserRegistered(id)
        }
    }

    override fun createChat(userIds: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            createChatUseCase.createChat(userIds)
        }
    }

    class Factory @Inject constructor(private val createChatUseCase: CreateChatUseCase) :
        CreateChatDialogFragmentViewModel.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CreateChatDialogFragmentViewModelImpl(createChatUseCase) as T
        }

    }
}