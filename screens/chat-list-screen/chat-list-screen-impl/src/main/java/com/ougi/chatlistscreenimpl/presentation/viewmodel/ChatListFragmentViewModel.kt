package com.ougi.chatlistscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.chatlistscreenimpl.domain.usecase.GetChatListUseCase
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatListFragmentViewModel {
    val chatListResultFlow: Flow<Result<List<Chat>?>>

    interface Factory : ViewModelProvider.Factory
}

class ChatListFragmentViewModelImpl(getChatListUseCase: GetChatListUseCase) :
    ViewModel(), ChatListFragmentViewModel {
    override val chatListResultFlow: Flow<Result<List<Chat>?>> =
        getChatListUseCase.chatListResultFlow

    class Factory @Inject constructor(private val getChatListUseCase: GetChatListUseCase) :
        ChatListFragmentViewModel.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatListFragmentViewModelImpl(getChatListUseCase) as T
        }

    }

}