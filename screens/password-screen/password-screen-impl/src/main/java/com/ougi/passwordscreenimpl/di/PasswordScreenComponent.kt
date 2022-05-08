package com.ougi.passwordscreenimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.passwordscreenapi.di.PasswordScreenApi
import com.ougi.passwordscreenimpl.presentation.view.CreatePasswordFragment
import com.ougi.passwordscreenimpl.presentation.view.EnterPasswordFragment
import dagger.Component

@[Component(
    modules = [PasswordScreenModule::class],
    dependencies = [PasswordScreenDeps::class]
) Feature]
interface PasswordScreenComponent : PasswordScreenApi {
    fun inject(createPasswordFragment: CreatePasswordFragment)
    fun inject(enterPasswordFragment: EnterPasswordFragment)

    companion object {
        fun newInstance(passwordScreenDeps: PasswordScreenDeps): PasswordScreenApi {
            return DaggerPasswordScreenComponent.builder()
                .passwordScreenDeps(passwordScreenDeps)
                .build()
        }
    }
}