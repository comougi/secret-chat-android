package com.ougi.secretchat

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.corecommon.base.di.DepsHolder
import com.ougi.coreutils.di.CoreUtilsComponentHolder
import com.ougi.coreutils.di.CoreUtilsDeps
import com.ougi.coreutils.utils.ContextProvider
import com.ougi.encryptionimpl.di.EncryptionFeatureComponentHolder
import com.ougi.encryptionimpl.di.EncryptionFeatureDeps
import com.ougi.networkimpl.di.CoreNetworkComponentHolder
import com.ougi.networkimpl.di.CoreNetworkDeps
import com.ougi.secretchat.data.ContextProviderImpl
import com.ougi.secretchat.di.AppComponentHolder
import com.ougi.secretchat.di.AppDeps
import com.ougi.websocketapi.data.WebSocketWorkerFactory
import com.ougi.websocketimpl.di.WebSocketFeatureComponentHolder
import com.ougi.websocketimpl.di.WebSocketFeatureDeps
import com.ougi.workmanagerinitializer.di.WorkManagerInititalizerComponentHolder
import com.ougi.workmanagerinitializer.di.WorkManagerInititalizerDeps

object Injector {

    fun injectAll(context: Context) {
        injectAppComponent()

        //core
        injectCoreUtilsComponent(context)
        injectCoreNetworkComponent()
        //injectCoreDbComponent()

        //features
        injectEncryptionFeatureComponent()
        injectWorkManagerInitializerComponent()
        injectWebSocketFeatureComponent()
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

    private fun injectWorkManagerInitializerComponent() {
        WorkManagerInititalizerComponentHolder.depsProvider = {
            object : DepsHolder<WorkManagerInititalizerDeps> {
                override val depsFactory: (DepsHolder<WorkManagerInititalizerDeps>) -> WorkManagerInititalizerDeps =
                    { deps ->
                        object : WorkManagerInititalizerDeps {
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val webSocketWorkerFactory: WebSocketWorkerFactory
                                get() = WebSocketFeatureComponentHolder.getInstance().webSocketWorkerFactory
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = deps
                        }
                    }
            }.deps
        }
    }

    private fun injectWebSocketFeatureComponent() {
        WebSocketFeatureComponentHolder.depsProvider = {
            object : DepsHolder<WebSocketFeatureDeps> {
                override val depsFactory: (DepsHolder<WebSocketFeatureDeps>) -> WebSocketFeatureDeps =
                    { deps ->
                        object : WebSocketFeatureDeps {
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = deps
                        }
                    }
            }.deps
        }
    }

    private fun injectEncryptionFeatureComponent() {
        EncryptionFeatureComponentHolder.depsProvider = {
            object : DepsHolder<EncryptionFeatureDeps> {
                override val depsFactory: (DepsHolder<EncryptionFeatureDeps>) -> EncryptionFeatureDeps =
                    { deps ->
                        object : EncryptionFeatureDeps {
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = deps
                        }
                    }
            }.deps
        }
    }

}