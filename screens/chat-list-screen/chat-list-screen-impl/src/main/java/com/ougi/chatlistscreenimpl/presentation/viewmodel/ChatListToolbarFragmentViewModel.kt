package com.ougi.chatlistscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.chatlistscreenimpl.domain.usecase.ObserveConnectionUseCase
import com.ougi.coreutils.utils.Result
import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface ChatListToolbarFragmentViewModel {
    val connectionStateResult: StateFlow<Result<WebSocketState?>>
    fun startObservingState()
    interface Factory : ViewModelProvider.Factory
}

class ChatListToolbarFragmentViewModelImpl(private val observeConnectionUseCase: ObserveConnectionUseCase) :
    ViewModel(), ChatListToolbarFragmentViewModel {

    override val connectionStateResult: StateFlow<Result<WebSocketState?>> =
        observeConnectionUseCase.connectionStateResult

    override fun startObservingState() {
        observeConnectionUseCase.startObservingState()
    }

    class Factory @Inject constructor(private val observeConnectionUseCase: ObserveConnectionUseCase) :
        ChatListToolbarFragmentViewModel.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatListToolbarFragmentViewModelImpl(observeConnectionUseCase) as T
        }

    }

}