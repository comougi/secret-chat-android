package com.ougi.chatlistscreenimpl.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ougi.chatlistscreenimpl.domain.usecase.GetUserInfoUseCase
import com.ougi.chatlistscreenimpl.domain.usecase.ObserveConnectionUseCase
import com.ougi.coreutils.utils.Result
import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatListToolbarFragmentViewModel {
    val connectionStateResult: Flow<Result<WebSocketState?>>
    suspend fun getUserId(): String
    interface Factory : ViewModelProvider.Factory
}

class ChatListToolbarFragmentViewModelImpl(
    observeConnectionUseCase: ObserveConnectionUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) :
    ViewModel(), ChatListToolbarFragmentViewModel {

    override val connectionStateResult: Flow<Result<WebSocketState?>> =
        observeConnectionUseCase.connectionStateResult

    override suspend fun getUserId(): String {
        return getUserInfoUseCase.getUserId()
    }

    class Factory @Inject constructor(
        private val observeConnectionUseCase: ObserveConnectionUseCase,
        private val getUserInfoUseCase: GetUserInfoUseCase
    ) :
        ChatListToolbarFragmentViewModel.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatListToolbarFragmentViewModelImpl(
                observeConnectionUseCase,
                getUserInfoUseCase
            ) as T
        }

    }

}