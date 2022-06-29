package com.ougi.chatscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.chatscreenimpl.domain.usecase.GetMessageListUseCase
import com.ougi.coreutils.utils.Result
import com.ougi.messagerepoapi.data.entities.PersonalMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow

interface MessageListFragmentViewModel {
    val personalMessagesResult: Flow<Result<List<PersonalMessage>>>

    interface Factory {
        fun create(chatId: String): ViewModelProvider.Factory
    }
}

class MessageListFragmentViewModelImpl(
    getMessageListUseCaseFactory: GetMessageListUseCase.Factory,
    chatId: String
) : ViewModel(), MessageListFragmentViewModel {

    private val getMessageListUseCase = getMessageListUseCaseFactory.create(chatId)

    override val personalMessagesResult: Flow<Result<List<PersonalMessage>>> =
        getMessageListUseCase.personalMessagesResult

    class Factory @AssistedInject constructor(
        private val getMessageListUseCaseFactory: GetMessageListUseCase.Factory,
        @Assisted(CHAT_ID) private val chatId: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MessageListFragmentViewModelImpl(getMessageListUseCaseFactory, chatId) as T
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory : MessageListFragmentViewModel.Factory {
        override fun create(@Assisted(CHAT_ID) chatId: String): Factory
    }

    companion object {
        private const val CHAT_ID = "chatId"
    }
}