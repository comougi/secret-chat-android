package com.ougi.daggermultimoduledemo.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.daggermultimoduledemo.data.AppApi

object AppComponentHolder : ComponentHolder<AppApi, AppDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<AppApi, AppDeps> { deps -> AppComponent.newInstance(deps) }

    override var depsProvider: (() -> AppDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): AppComponent =
        componentHolderDelegate.getInstance() as AppComponent

}