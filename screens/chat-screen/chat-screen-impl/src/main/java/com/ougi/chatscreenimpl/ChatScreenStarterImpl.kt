package com.ougi.chatscreenimpl

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import com.ougi.chatscreenapi.data.ChatScreenStarter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ChatScreenStarterImpl @AssistedInject constructor(
    @Assisted(CHAT_ID) private val chatId: String
) : ChatScreenStarter {

    override fun start(navController: NavController) {
        val deepLinkRequest = NavDeepLinkRequest.Builder
            .fromUri(Uri.parse("https://www.scsc.com/chat/$chatId"))
            .build()
        navController.navigate(deepLinkRequest)
    }

    @AssistedFactory
    interface Factory : ChatScreenStarter.Factory {
        override fun create(@Assisted(CHAT_ID) chatId: String): ChatScreenStarterImpl
    }

    companion object {
        private const val CHAT_ID = "chatId"
    }


}