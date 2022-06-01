package com.ougi.chatlistscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.chatlistscreenimpl.domain.usecase.FetchChatListUseCase
import com.ougi.chatrepoapi.data.entity.Chat
import com.ougi.coreutils.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatListFragmentViewModel {
    val chatListResultFlow: Flow<Result<List<Chat>?>>

    interface Factory : ViewModelProvider.Factory
}

class ChatListFragmentViewModelImpl(fetchChatListUseCase: FetchChatListUseCase) :
    ViewModel(), ChatListFragmentViewModel {
    override val chatListResultFlow: Flow<Result<List<Chat>?>> =
        fetchChatListUseCase.chatListResultFlow

    class Factory @Inject constructor(private val fetchChatListUseCase: FetchChatListUseCase) :
        ChatListFragmentViewModel.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatListFragmentViewModelImpl(fetchChatListUseCase) as T
        }

    }

}