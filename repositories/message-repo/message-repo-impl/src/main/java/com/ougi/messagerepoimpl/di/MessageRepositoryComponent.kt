package com.ougi.messagerepoimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.messagerepoapi.di.MessageRepositoryApi
import dagger.Component

@[Component(
    modules = [MessageRepositoryModule::class],
    dependencies = [MessageRepositoryDeps::class]
) Feature]
interface MessageRepositoryComponent : MessageRepositoryApi {
    companion object {
        fun newInstance(messageRepositoryDeps: MessageRepositoryDeps): MessageRepositoryApi {
            return DaggerMessageRepositoryComponent.builder()
                .messageRepositoryDeps(messageRepositoryDeps)
                .build()
        }
    }
}