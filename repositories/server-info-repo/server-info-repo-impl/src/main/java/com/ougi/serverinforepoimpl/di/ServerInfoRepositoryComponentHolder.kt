package com.ougi.serverinforepoimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.serverinforepoapi.di.ServerInfoRepositoryApi

object ServerInfoRepositoryComponentHolder :
    ComponentHolder<ServerInfoRepositoryApi, ServerInfoRepositoryDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<ServerInfoRepositoryApi, ServerInfoRepositoryDeps> { deps ->
            ServerInfoRepositoryComponent.newInstance(deps)
        }

    override var depsProvider: (() -> ServerInfoRepositoryDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): ServerInfoRepositoryComponent =
        componentHolderDelegate.getInstance() as ServerInfoRepositoryComponent

}