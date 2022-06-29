package com.ougi.chatscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ougi.chatscreenimpl.domain.usecase.SendMessageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface MessageSenderFragmentViewModel {
    fun sendMessage(messageText: String)
    interface Factory {
        fun create(chatId: String): ViewModelProvider.Factory
    }
}

class MessageSenderFragmentViewModelImpl(
    private val sendMessageUseCase: SendMessageUseCase,
    private val chatId: String
) : ViewModel(), MessageSenderFragmentViewModel {

    override fun sendMessage(messageText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendMessageUseCase.sendMessage(messageText, chatId)
        }
    }

    class Factory @AssistedInject constructor(
        private val sendMessageUseCase: SendMessageUseCase,
        @Assisted(CHAT_ID) private val chatId: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageSenderFragmentViewModelImpl(sendMessageUseCase, chatId) as T
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory : MessageSenderFragmentViewModel.Factory {
        override fun create(@Assisted(CHAT_ID) chatId: String): Factory
    }

    companion object {
      private const val CHAT_ID = "chatId"
    }
}