package com.ougi.networkimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.networkapi.di.CoreNetworkApi

object CoreNetworkComponentHolder : ComponentHolder<CoreNetworkApi, CoreNetworkDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<CoreNetworkApi, CoreNetworkDeps> { deps ->
            CoreNetworkComponent.newInstance(deps)
        }

    override var depsProvider: (() -> CoreNetworkDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): CoreNetworkComponent =
        componentHolderDelegate.getInstance() as CoreNetworkComponent

}