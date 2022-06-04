package com.ougi.chatlistscreenimpl.di

import com.ougi.chatlistscreenapi.di.ChatListScreenApi
import com.ougi.chatlistscreenimpl.presentation.view.ChatListFragment
import com.ougi.chatlistscreenimpl.presentation.view.ChatListToolbarFragment
import com.ougi.chatlistscreenimpl.presentation.view.CreateChatDialogFragment
import com.ougi.coreutils.dagger.Feature
import dagger.Component

@[Component(
    modules = [ChatListScreenModule::class],
    dependencies = [ChatListScreenDeps::class]
) Feature]
interface ChatListScreenComponent : ChatListScreenApi {
    fun inject(chatListFragment: ChatListFragment)
    fun inject(chatListRecyclerViewFragment: ChatListToolbarFragment)
    fun inject(createChatDialogFragment: CreateChatDialogFragment)

    companion object {
        fun newInstance(chatListScreenDeps: ChatListScreenDeps): ChatListScreenApi {
            return DaggerChatListScreenComponent.builder()
                .chatListScreenDeps(chatListScreenDeps)
                .build()
        }
    }
}