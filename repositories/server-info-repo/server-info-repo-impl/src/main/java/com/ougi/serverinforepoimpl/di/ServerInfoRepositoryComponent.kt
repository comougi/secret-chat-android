package com.ougi.serverinforepoimpl.di

import com.ougi.coreutils.dagger.Repo
import com.ougi.serverinforepoapi.di.ServerInfoRepositoryApi
import dagger.Component

@[Component(
    modules = [ServerInfoRepositoryModule::class],
    dependencies = [ServerInfoRepositoryDeps::class]
) Repo]
interface ServerInfoRepositoryComponent : ServerInfoRepositoryApi {
    companion object {
        fun newInstance(serverInfoRepositoryDeps: ServerInfoRepositoryDeps): ServerInfoRepositoryApi {
            return DaggerServerInfoRepositoryComponent.builder()
                .serverInfoRepositoryDeps(serverInfoRepositoryDeps)
                .build()
        }
    }
}