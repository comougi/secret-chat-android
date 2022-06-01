package com.ougi.chatlistscreenimpl.domain.repository

import com.ougi.coreutils.utils.Result
import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.StateFlow

interface MessagingConnectionStateRepository {
    val connectionStateResult: StateFlow<Result<WebSocketState?>>
    fun startObservingState()
}