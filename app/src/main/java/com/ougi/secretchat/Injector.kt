package com.ougi.secretchat

import android.content.Context
import com.ougi.chatlistscreenimpl.di.ChatListScreenComponentHolder
import com.ougi.chatlistscreenimpl.di.ChatListScreenDeps
import com.ougi.chatrepoapi.data.database.ChatDatabaseDao
import com.ougi.chatrepoapi.data.repository.ChatRepository
import com.ougi.chatrepoimpl.di.ChatRepositoryComponentHolder
import com.ougi.chatrepoimpl.di.ChatRepositoryDeps
import com.ougi.corecommon.base.ScreenStarter
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.corecommon.base.di.DepsHolder
import com.ougi.coreutils.di.CoreUtilsComponentHolder
import com.ougi.coreutils.di.CoreUtilsDeps
import com.ougi.dbapi.data.DbClientApi
import com.ougi.dbimpl.di.CoreDbComponentHolder
import com.ougi.dbimpl.di.CoreDbDeps
import com.ougi.encryptionapi.data.EncryptedDataStoreApi
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionimpl.di.EncryptionFeatureComponentHolder
import com.ougi.encryptionimpl.di.EncryptionFeatureDeps
import com.ougi.messagingapi.data.MessagingFeatureClientApi
import com.ougi.messagingapi.data.MessagingFeatureWorkerFactory
import com.ougi.messagingimpl.di.MessagingFeatureComponentHolder
import com.ougi.messagingimpl.di.MessagingFeatureDeps
import com.ougi.networkapi.data.NetworkClientApi
import com.ougi.networkimpl.di.CoreNetworkComponentHolder
import com.ougi.networkimpl.di.CoreNetworkDeps
import com.ougi.passwordscreenapi.data.PasswordScreenStarter
import com.ougi.passwordscreenimpl.di.PasswordScreenComponentHolder
import com.ougi.passwordscreenimpl.di.PasswordScreenDeps
import com.ougi.secretchat.di.AppComponentHolder
import com.ougi.secretchat.di.AppDeps
import com.ougi.serverinforepoapi.data.repository.ServerInfoRepository
import com.ougi.serverinforepoimpl.di.ServerInfoRepositoryComponentHolder
import com.ougi.serverinforepoimpl.di.ServerInfoRepositoryDeps
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import com.ougi.userrepoapi.data.repository.UserRepository
import com.ougi.userrepoimpl.di.UserRepositoryComponentHolder
import com.ougi.userrepoimpl.di.UserRepositoryDeps
import com.ougi.websocketapi.data.WebSocketClientApi
import com.ougi.websocketimpl.di.WebSocketFeatureComponentHolder
import com.ougi.websocketimpl.di.WebSocketFeatureDeps
import com.ougi.workmanagerinitializer.di.WorkManagerInititalizerComponentHolder
import com.ougi.workmanagerinitializer.di.WorkManagerInititalizerDeps

object Injector {

    fun injectAll(context: Context) {
        injectAppComponent(context)

        //core
        injectCoreUtilsComponent(context)
        injectCoreNetworkComponent()
        injectCoreDbComponent()

        //features
        injectEncryptionFeatureComponent()
        injectWorkManagerInitializerComponent()
        injectWebSocketFeatureComponent()
        injectMessagingFeatureComponent()

        //screens
        injectPasswordScreenComponent()
        injectChatListScreenComponent()

        //repositories
        injectChatRepositoryComponent()
        injectServerInfoRepositoryComponent()
        injectUserRepositoryComponent()
    }

    private fun injectAppComponent(context: Context) {
        AppComponentHolder.depsProvider = {
            object : DepsHolder<AppDeps> {
                override val depsFactory: (DepsHolder<AppDeps>) -> AppDeps = { depsHolder ->
                    object : AppDeps {
                        override val context: Context = context
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
                            override val context: Context
                                get() = context
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

    private fun injectCoreDbComponent() {
        CoreDbComponentHolder.depsProvider = {
            object : DepsHolder<CoreDbDeps> {
                override val depsFactory: (DepsHolder<CoreDbDeps>) -> CoreDbDeps = { depsHolder ->
                    object : CoreDbDeps {
                        override val context: Context
                            get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                        override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                    }
                }
            }.deps
        }
    }

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
                            override val messagingFeatureWorkerFactory: MessagingFeatureWorkerFactory
                                get() = MessagingFeatureComponentHolder.getInstance().messagingFeatureWorkerFactory
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
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val passwordScreenStarter: PasswordScreenStarter
                                get() = PasswordScreenComponentHolder.getInstance().passwordScreenStarter
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    private fun injectMessagingFeatureComponent() {
        MessagingFeatureComponentHolder.depsProvider = {
            object : DepsHolder<MessagingFeatureDeps> {
                override val depsFactory: (DepsHolder<MessagingFeatureDeps>) -> MessagingFeatureDeps =
                    { depsHolder ->
                        object : MessagingFeatureDeps {
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val serverInfoRepository: ServerInfoRepository
                                get() = ServerInfoRepositoryComponentHolder.getInstance().serverInfoRepository
                            override val webSocketClientApi: WebSocketClientApi
                                get() = WebSocketFeatureComponentHolder.getInstance().webSocketClientApi
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    /**
    Screen modules injection
     **/

    private fun injectPasswordScreenComponent() {
        PasswordScreenComponentHolder.depsProvider = {
            object : DepsHolder<PasswordScreenDeps> {
                override val depsFactory: (DepsHolder<PasswordScreenDeps>) -> PasswordScreenDeps =
                    { depsHolder ->
                        object : PasswordScreenDeps {
                            override val keyStorageApi: KeyStorageApi
                                get() = EncryptionFeatureComponentHolder.getInstance().keyStorageApi
                            override val keyGenerationUtils: KeyGenerationUtils
                                get() = EncryptionFeatureComponentHolder.getInstance().keyGenerationUtils
                            override val userRepository: UserRepository
                                get() = UserRepositoryComponentHolder.getInstance().userRepository
                            override val userRepositoryDataStoreApi: UserRepositoryDataStoreApi
                                get() = UserRepositoryComponentHolder.getInstance().userRepositoryDataStoreApi
                            override val mainActivityStarter: ScreenStarter
                                get() = AppComponentHolder.getInstance().mainActivityStarter
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    private fun injectChatListScreenComponent() {
        ChatListScreenComponentHolder.depsProvider = {
            object : DepsHolder<ChatListScreenDeps> {
                override val depsFactory: (DepsHolder<ChatListScreenDeps>) -> ChatListScreenDeps =
                    { depsHolder ->
                        object : ChatListScreenDeps {
                            override val chatDatabaseDao: ChatDatabaseDao
                                get() = ChatRepositoryComponentHolder.getInstance().chatDatabaseDao
                            override val messagingFeatureClientApi: MessagingFeatureClientApi
                                get() = MessagingFeatureComponentHolder.getInstance().messagingFeatureClientApi
                            override val userRepository: UserRepository
                                get() = UserRepositoryComponentHolder.getInstance().userRepository
                            override val chatRepository: ChatRepository
                                get() = ChatRepositoryComponentHolder.getInstance().chatRepository
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    /**
    Repository modules injection
     **/

    private fun injectChatRepositoryComponent() {
        ChatRepositoryComponentHolder.depsProvider = {
            object : DepsHolder<ChatRepositoryDeps> {
                override val depsFactory: (DepsHolder<ChatRepositoryDeps>) -> ChatRepositoryDeps =
                    { depsHolder ->
                        object : ChatRepositoryDeps {
                            override val userRepositoryDataString: UserRepositoryDataStoreApi
                                get() = UserRepositoryComponentHolder.getInstance().userRepositoryDataStoreApi
                            override val encryptionClientApi: EncryptionClientApi
                                get() = EncryptionFeatureComponentHolder.getInstance().encryptionClientApi
                            override val networkClientApi: NetworkClientApi
                                get() = CoreNetworkComponentHolder.getInstance().networkClientApi
                            override val dbClientApi: DbClientApi
                                get() = CoreDbComponentHolder.getInstance().dbClientApi
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    private fun injectServerInfoRepositoryComponent() {
        ServerInfoRepositoryComponentHolder.depsProvider = {
            object : DepsHolder<ServerInfoRepositoryDeps> {
                override val depsFactory: (DepsHolder<ServerInfoRepositoryDeps>) -> ServerInfoRepositoryDeps =
                    { depsHolder ->
                        object : ServerInfoRepositoryDeps {
                            override val context: Context
                                get() = CoreUtilsComponentHolder.getInstance().contextProvider.context
                            override val networkClientApi: NetworkClientApi
                                get() = CoreNetworkComponentHolder.getInstance().networkClientApi
                            override val userRepositoryDataStoreApi: UserRepositoryDataStoreApi
                                get() = UserRepositoryComponentHolder.getInstance().userRepositoryDataStoreApi
                            override val encryptionClientApi: EncryptionClientApi
                                get() = EncryptionFeatureComponentHolder.getInstance().encryptionClientApi
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

    private fun injectUserRepositoryComponent() {
        UserRepositoryComponentHolder.depsProvider = {
            object : DepsHolder<UserRepositoryDeps> {
                override val depsFactory: (DepsHolder<UserRepositoryDeps>) -> UserRepositoryDeps =
                    { depsHolder ->
                        object : UserRepositoryDeps {
                            override val networkClientApi: NetworkClientApi
                                get() = CoreNetworkComponentHolder.getInstance().networkClientApi
                            override val encryptionClientApi: EncryptionClientApi
                                get() = EncryptionFeatureComponentHolder.getInstance().encryptionClientApi
                            override val encryptedDataStoreApi: EncryptedDataStoreApi
                                get() = EncryptionFeatureComponentHolder.getInstance().encryptedDataStoreApi
                            override val keyStorageApi: KeyStorageApi
                                get() = EncryptionFeatureComponentHolder.getInstance().keyStorageApi
                            override val depsHolder: DepsHolder<out BaseFeatureDeps> = depsHolder
                        }
                    }
            }.deps
        }
    }

}