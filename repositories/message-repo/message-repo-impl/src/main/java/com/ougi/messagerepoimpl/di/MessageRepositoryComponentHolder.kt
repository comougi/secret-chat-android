package com.ougi.messagerepoimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.messagerepoapi.di.MessageRepositoryApi

object MessageRepositoryComponentHolder :
    ComponentHolder<MessageRepositoryApi, MessageRepositoryDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<MessageRepositoryApi, MessageRepositoryDeps> { deps ->
            MessageRepositoryComponent.newInstance(deps)
        }

    override var depsProvider: (() -> MessageRepositoryDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): MessageRepositoryComponent =
        componentHolderDelegate.getInstance() as MessageRepositoryComponent
}