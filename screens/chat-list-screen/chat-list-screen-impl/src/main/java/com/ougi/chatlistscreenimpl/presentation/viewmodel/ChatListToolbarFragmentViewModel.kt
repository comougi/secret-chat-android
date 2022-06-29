package com.ougi.chatlistscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.chatlistscreenimpl.domain.usecase.ObserveConnectionUseCase
import com.ougi.coreutils.utils.Result
import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatListToolbarFragmentViewModel {
    val connectionStateResult: Flow<Result<WebSocketState?>>

    interface Factory : ViewModelProvider.Factory
}

class ChatListToolbarFragmentViewModelImpl(observeConnectionUseCase: ObserveConnectionUseCase) :
    ViewModel(), ChatListToolbarFragmentViewModel {

    override val connectionStateResult: Flow<Result<WebSocketState?>> =
        observeConnectionUseCase.connectionStateResult

    class Factory @Inject constructor(private val observeConnectionUseCase: ObserveConnectionUseCase) :
        ChatListToolbarFragmentViewModel.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatListToolbarFragmentViewModelImpl(observeConnectionUseCase) as T
        }

    }

}