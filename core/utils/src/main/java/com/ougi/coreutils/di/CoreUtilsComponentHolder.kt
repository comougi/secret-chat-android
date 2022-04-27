package com.ougi.coreutils.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate

object CoreUtilsComponentHolder : ComponentHolder<CoreUtilsApi, CoreUtilsDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<CoreUtilsApi, CoreUtilsDeps> { deps ->
            CoreUtilsComponent.newInstance(deps)
        }

    override var depsProvider: (() -> CoreUtilsDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): CoreUtilsComponent =
        componentHolderDelegate.getInstance() as CoreUtilsComponent

}