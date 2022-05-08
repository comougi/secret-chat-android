package com.ougi.chatrepoimpl.di

import com.ougi.chatrepoapi.di.ChatRepositoryApi
import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate

object ChatRepositoryComponentHolder : ComponentHolder<ChatRepositoryApi, ChatRepositoryDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<ChatRepositoryApi, ChatRepositoryDeps> { deps ->
            ChatRepositoryComponent.newInstance(deps)
        }

    override var depsProvider: (() -> ChatRepositoryDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): ChatRepositoryComponent =
        componentHolderDelegate.getInstance() as ChatRepositoryComponent
}