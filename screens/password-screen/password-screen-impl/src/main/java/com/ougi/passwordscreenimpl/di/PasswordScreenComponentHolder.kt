package com.ougi.passwordscreenimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.passwordscreenapi.di.PasswordScreenApi

object PasswordScreenComponentHolder : ComponentHolder<PasswordScreenApi, PasswordScreenDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<PasswordScreenApi, PasswordScreenDeps> { deps ->
            PasswordScreenComponent.newInstance(deps)
        }

    override var depsProvider: (() -> PasswordScreenDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): PasswordScreenComponent =
        componentHolderDelegate.getInstance() as PasswordScreenComponent
}