package com.ougi.userrepoimpl.di

import com.ougi.coreutils.dagger.Repo
import com.ougi.userrepoapi.di.UserRepositoryApi
import dagger.Component

@[Component(
    modules = [UserRepositoryModule::class],
    dependencies = [UserRepositoryDeps::class]
) Repo]
interface UserRepositoryComponent : UserRepositoryApi {
    companion object {
        fun newInstance(userRepositoryDeps: UserRepositoryDeps): UserRepositoryApi {
            return DaggerUserRepositoryComponent.builder()
                .userRepositoryDeps(userRepositoryDeps)
                .build()
        }
    }
}