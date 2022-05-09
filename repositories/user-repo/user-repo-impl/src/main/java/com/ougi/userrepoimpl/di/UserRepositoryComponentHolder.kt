package com.ougi.userrepoimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.userrepoapi.di.UserRepositoryApi

object UserRepositoryComponentHolder : ComponentHolder<UserRepositoryApi, UserRepositoryDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<UserRepositoryApi, UserRepositoryDeps> { deps ->
            UserRepositoryComponent.newInstance(deps)
        }

    override var depsProvider: (() -> UserRepositoryDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): UserRepositoryComponent =
        componentHolderDelegate.depsProvider as UserRepositoryComponent
}