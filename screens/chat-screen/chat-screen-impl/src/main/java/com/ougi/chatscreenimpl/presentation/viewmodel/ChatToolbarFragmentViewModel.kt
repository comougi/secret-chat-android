package com.ougi.chatscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.chatscreenimpl.domain.usecase.GetChatInfoUseCase
import javax.inject.Inject

interface ChatToolbarFragmentViewModel {
    suspend fun getChatTitle(chatId: String): String?
    interface Factory : ViewModelProvider.Factory
}

class ChatToolbarFragmentViewModelImpl(private val getChatInfoUseCase: GetChatInfoUseCase) :
    ViewModel(), ChatToolbarFragmentViewModel {

    override suspend fun getChatTitle(chatId: String): String? {
        return getChatInfoUseCase.getChatTitle(chatId)
    }

    class Factory @Inject constructor(private val getChatInfoUseCase: GetChatInfoUseCase) :
        ChatToolbarFragmentViewModel.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatToolbarFragmentViewModelImpl(getChatInfoUseCase) as T
        }
    }

}