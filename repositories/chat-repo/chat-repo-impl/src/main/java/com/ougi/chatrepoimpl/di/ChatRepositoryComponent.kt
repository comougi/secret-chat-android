package com.ougi.chatrepoimpl.di

import com.ougi.chatrepoapi.di.ChatRepositoryApi
import com.ougi.coreutils.dagger.Repo
import dagger.Component

@[Component(
    modules = [ChatRepositoryModule::class],
    dependencies = [ChatRepositoryDeps::class]
) Repo]
interface ChatRepositoryComponent : ChatRepositoryApi {
    companion object {
        fun newInstance(chatRepositoryDeps: ChatRepositoryDeps): ChatRepositoryApi {
            return DaggerChatRepositoryComponent.builder()
                .chatRepositoryDeps(chatRepositoryDeps)
                .build()
        }
    }
}