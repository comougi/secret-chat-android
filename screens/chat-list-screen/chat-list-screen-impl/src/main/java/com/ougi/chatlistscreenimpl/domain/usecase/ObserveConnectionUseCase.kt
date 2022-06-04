package com.ougi.chatlistscreenimpl.domain.usecase

import com.ougi.chatlistscreenimpl.R
import com.ougi.coreutils.utils.Result
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.websocketapi.data.entities.WebSocketState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ObserveConnectionUseCase {
    val connectionStateResult: Flow<Result<WebSocketState?>>
}

class ObserveConnectionUseCaseImpl @Inject constructor(messagingFeatureClientApi: MessagingFeatureClientApi) :
    ObserveConnectionUseCase {
    override val connectionStateResult: Flow<Result<WebSocketState?>> =
        messagingFeatureClientApi.webSocketStateAsFlow()
            .map { state ->
                when (state) {
                    WebSocketState.CONNECTING -> Result.Loading(R.string.connecting)
                    WebSocketState.CONNECTED -> Result.Success(state, R.string.connected)
                    WebSocketState.CLOSED -> Result.Error(R.string.connection_failed)
                    else -> Result.Error(R.string.no_connection)
                }
            }

}