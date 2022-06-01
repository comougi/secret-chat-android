package com.ougi.chatlistscreenimpl.data

import com.ougi.chatlistscreenimpl.R
import com.ougi.chatlistscreenimpl.domain.repository.MessagingConnectionStateRepository
import com.ougi.coreutils.utils.Result
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MessagingConnectionStateRepositoryImpl @Inject constructor(
    private val messagingFeatureClientApi: MessagingFeatureClientApi
) : MessagingConnectionStateRepository {

    override val connectionStateResult: MutableStateFlow<Result<WebSocketState?>> =
        MutableStateFlow(Result.Loading(R.string.connecting))

    override fun startObservingState() {
        messagingFeatureClientApi.observeState { webSocketState ->
            connectionStateResult.value = when (webSocketState) {
                WebSocketState.CONNECTING -> Result.Loading(R.string.connecting)
                WebSocketState.CONNECTED -> Result.Success(webSocketState, R.string.connected)
                WebSocketState.CLOSED -> Result.Error(R.string.connection_failed)
                else -> Result.Error(R.string.no_connection)
            }
        }
    }
}