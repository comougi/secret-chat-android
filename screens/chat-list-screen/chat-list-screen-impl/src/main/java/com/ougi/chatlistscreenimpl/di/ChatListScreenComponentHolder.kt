package com.ougi.chatlistscreenimpl.di

import com.ougi.chatlistscreenapi.di.ChatListScreenApi
import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate

object ChatListScreenComponentHolder : ComponentHolder<ChatListScreenApi, ChatListScreenDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<ChatListScreenApi, ChatListScreenDeps> { deps ->
            ChatListScreenComponent.newInstance(deps)
        }

    override var depsProvider: (() -> ChatListScreenDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): ChatListScreenComponent =
        componentHolderDelegate.getInstance() as ChatListScreenComponent

}