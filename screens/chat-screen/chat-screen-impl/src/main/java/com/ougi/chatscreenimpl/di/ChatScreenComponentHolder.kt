package com.ougi.chatscreenimpl.di

import com.ougi.chatscreenapi.di.ChatScreenApi
import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate

object ChatScreenComponentHolder : ComponentHolder<ChatScreenApi, ChatScreenDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<ChatScreenApi, ChatScreenDeps> { deps ->
            ChatScreenComponent.getInstance(deps)
        }
    override var depsProvider: (() -> ChatScreenDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): ChatScreenComponent =
        componentHolderDelegate.getInstance() as ChatScreenComponent
}