package com.ougi.chatlistscreenimpl.domain.usecase

import com.ougi.chatlistscreenimpl.domain.repository.MessagingConnectionStateRepository
import com.ougi.coreutils.utils.Result
import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface ObserveConnectionUseCase {
    val connectionStateResult: StateFlow<Result<WebSocketState?>>
    fun startObservingState()
}

class ObserveConnectionUseCaseImpl @Inject constructor(private val messagingConnectionStateRepository: MessagingConnectionStateRepository) :
    ObserveConnectionUseCase {
    override val connectionStateResult: StateFlow<Result<WebSocketState?>> =
        messagingConnectionStateRepository.connectionStateResult

    override fun startObservingState() {
        messagingConnectionStateRepository.startObservingState()
    }

}