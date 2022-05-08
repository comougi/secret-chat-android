package com.ougi.chatlistscreenimpl.di

import com.ougi.chatlistscreenapi.di.ChatListScreenApi
import com.ougi.coreutils.dagger.Feature
import dagger.Component

@[Component(
    modules = [ChatListScreenModule::class],
    dependencies = [ChatListScreenDeps::class]
) Feature]
interface ChatListScreenComponent : ChatListScreenApi {
    companion object {
        fun newInstance(chatListScreenDeps: ChatListScreenDeps): ChatListScreenApi {
            return DaggerChatListScreenComponent.builder()
                .chatListScreenDeps(chatListScreenDeps)
                .build()
        }
    }
}