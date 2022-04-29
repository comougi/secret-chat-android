package com.ougi.secretchat

import android.content.Context
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.corecommon.base.di.DepsHolder
import com.ougi.coreutils.di.CoreUtilsComponentHolder
import com.ougi.coreutils.di.CoreUtilsDeps
import com.ougi.coreutils.utils.ContextProvider
import com.ougi.datastoreimpl.di.DataStoreFeatureComponentHolder
import com.ougi.datastoreimpl.di.DataStoreFeatureDeps
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
        injectDataStoreFeatureComponent()
        injectWorkManagerInitializerComponent()
        injectWebSocketFeatureComponent()
    }

    private fun injectAppComponent() {
        AppComponentHolder.depsProvider = {
            object : DepsHolder<AppDeps> {
                override val depsFactory: (DepsHolder<AppDeps>) -> AppDeps = { depsHolder ->
                    object : AppDeps {
                        override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
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
                override val depsFactory: (DepsHolder<CoreUtilsDeps>) -> CoreUtilsDeps =
                    { depsHolder ->
                        object : CoreUtilsDeps {
                            override val contextProvider: ContextProvider =
                                ContextProviderImpl(context)
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    private fun injectCoreNetworkComponent() {
        CoreNetworkComponentHolder.depsProvider = {
            object : DepsHolder<CoreNetworkDeps> {
                override val depsFactory: (DepsHolder<CoreNetworkDeps>) -> CoreNetworkDeps =
                    { depsHolder ->
                        object : CoreNetworkDeps {
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

//    private fun injectCoreDbComponent() {
//        CoreDbComponentHolder.depsProvider = {
//            object : DepsHolder<CoreDbDeps> {
//                override val depsFactory: (DepsHolder<CoreDbDeps>) -> CoreDbDeps = { depsHolder ->
//                    object : CoreDbDeps {
//                        override val contextProvider: ContextProvider
//                            get() = CoreUtilsComponentHolder.getInstance().contextProvider
//                        override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
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
                    { depsHolder ->
                        object : WorkManagerInititalizerDeps {
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val webSocketWorkerFactory: WebSocketWorkerFactory
                                get() = WebSocketFeatureComponentHolder.getInstance().webSocketWorkerFactory
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    private fun injectWebSocketFeatureComponent() {
        WebSocketFeatureComponentHolder.depsProvider = {
            object : DepsHolder<WebSocketFeatureDeps> {
                override val depsFactory: (DepsHolder<WebSocketFeatureDeps>) -> WebSocketFeatureDeps =
                    { depsHolder ->
                        object : WebSocketFeatureDeps {
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    private fun injectEncryptionFeatureComponent() {
        EncryptionFeatureComponentHolder.depsProvider = {
            object : DepsHolder<EncryptionFeatureDeps> {
                override val depsFactory: (DepsHolder<EncryptionFeatureDeps>) -> EncryptionFeatureDeps =
                    { depsHolder ->
                        object : EncryptionFeatureDeps {
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    private fun injectDataStoreFeatureComponent() {
        DataStoreFeatureComponentHolder.depsProvider = {
            object : DepsHolder<DataStoreFeatureDeps> {
                override val depsFactory: (DepsHolder<DataStoreFeatureDeps>) -> DataStoreFeatureDeps =
                    { depsHolder ->
                        object : DataStoreFeatureDeps {
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

}