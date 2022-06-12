package com.ougi.chatscreenimpl.di

import com.ougi.chatscreenapi.di.ChatScreenApi
import com.ougi.chatscreenimpl.presentation.view.MessageListFragment
import com.ougi.chatscreenimpl.presentation.view.MessageSenderFragment
import com.ougi.coreutils.dagger.Feature
import dagger.Component

@[Component(
    modules = [ChatScreenModule::class],
    dependencies = [ChatScreenDeps::class]
) Feature]
interface ChatScreenComponent : ChatScreenApi {
    fun inject(messageSenderFragment: MessageSenderFragment)
    fun inject(messageListFragment: MessageListFragment)

    companion object {
        fun getInstance(chatScreenDeps: ChatScreenDeps): ChatScreenApi {
            return DaggerChatScreenComponent.builder()
                .chatScreenDeps(chatScreenDeps)
                .build()
        }
    }
}