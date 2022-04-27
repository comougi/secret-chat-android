package com.ougi.secretchat

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.corecommon.base.di.DepsHolder
import com.ougi.coreutils.di.CoreUtilsComponentHolder
import com.ougi.coreutils.di.CoreUtilsDeps
import com.ougi.coreutils.utils.ContextProvider
import com.ougi.networkimpl.di.CoreNetworkComponentHolder
import com.ougi.networkimpl.di.CoreNetworkDeps
import com.ougi.secretchat.data.ContextProviderImpl
import com.ougi.secretchat.di.AppComponentHolder
import com.ougi.secretchat.di.AppDeps

object Injector {

    fun injectAll(context: Context) {
        injectAppComponent()

        //core
        injectCoreUtilsComponent(context)
        injectCoreNetworkComponent()
        //injectCoreDbComponent()

        //features
    }

    private fun injectAppComponent() {
        AppComponentHolder.depsProvider = {
            object : DepsHolder<AppDeps> {
                override val depsFactory: (DepsHolder<AppDeps>) -> AppDeps = { deps ->
                    object : AppDeps {
                        override val depsHolder: DepsHolder<out BaseFeatureDeps> = deps
                    }
                }
            }.deps
        }
    }

    /**
    Core modules injection
     **/

    private fun injectCoreUtilsComponent(context: Context) {
        CoreUtilsComponentHolder.depsProvider = {
            object : DepsHolder<CoreUtilsDeps> {
                override val depsFactory: (DepsHolder<CoreUtilsDeps>) -> CoreUtilsDeps = { deps ->
                    object : CoreUtilsDeps {
                        override val contextProvider: ContextProvider = ContextProviderImpl(context)
                        override val depsHolder: DepsHolder<out BaseFeatureDeps> = deps
                    }
                }
            }.deps
        }
    }

    private fun injectCoreNetworkComponent() {
        CoreNetworkComponentHolder.depsProvider = {
            object : DepsHolder<CoreNetworkDeps> {
                override val depsFactory: (DepsHolder<CoreNetworkDeps>) -> CoreNetworkDeps =
                    { deps ->
                        object : CoreNetworkDeps {
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = deps
                        }
                    }
            }.deps
        }
    }

//    private fun injectCoreDbComponent() {
//        CoreDbComponentHolder.depsProvider = {
//            object : DepsHolder<CoreDbDeps> {
//                override val depsFactory: (DepsHolder<CoreDbDeps>) -> CoreDbDeps = { deps ->
//                    object : CoreDbDeps {
//                        override val contextProvider: ContextProvider
//                            get() = CoreUtilsComponentHolder.getInstance().contextProvider
//                        override val depsHolder: DepsHolder<out BaseFeatureDeps> = deps
//                    }
//                }
//            }.deps
//        }
//    }

    /**
    Feature modules injection
     **/


}