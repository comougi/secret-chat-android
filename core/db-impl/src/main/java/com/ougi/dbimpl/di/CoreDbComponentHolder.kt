package com.ougi.dbimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.dbapi.di.CoreDbApi

object CoreDbComponentHolder : ComponentHolder<CoreDbApi, CoreDbDeps> {
    private val componentHolderDelegate =
        ComponentHolderDelegate<CoreDbApi, CoreDbDeps> { deps -> CoreDbComponent.newInstance(deps) }

    override var depsProvider: (() -> CoreDbDeps)? by componentHolderDelegate::depsProvider
    override fun getInstance(): CoreDbComponent =
        componentHolderDelegate.getInstance() as CoreDbComponent

}