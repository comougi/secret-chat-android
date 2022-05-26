package com.ougi.passwordscreenimpl.di

import android.content.Context
import com.ougi.corecommon.base.ScreenStarter
import com.ougi.corecommon.base.di.BaseFeatureDeps
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.userrepoapi.data.datastore.UserRepositoryDataStoreApi
import com.ougi.userrepoapi.data.repository.UserRepository

interface PasswordScreenDeps : BaseFeatureDeps {
    val keyStorageApi: KeyStorageApi
    val keyGenerationUtils: KeyGenerationUtils
    val userRepository: UserRepository
    val userRepositoryDataStoreApi: UserRepositoryDataStoreApi
    val mainActivityStarter: ScreenStarter
    val context: Context
}