package com.ougi.encryptionimpl.di

import com.ougi.encryptionapi.data.EncryptedDataStoreApi
import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.KeyStorageApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionimpl.data.EncryptedDataStoreApiImpl
import com.ougi.encryptionimpl.data.EncryptionClientApiImpl
import com.ougi.encryptionimpl.data.KeyStorageApiImpl
import com.ougi.encryptionimpl.data.utils.EncryptionUtilsImpl
import com.ougi.encryptionimpl.data.utils.HashUtilsImpl
import com.ougi.encryptionimpl.data.utils.KeyGenerationUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface EncryptionFeatureModule {

    @[Singleton Binds]
    fun bindHashUtils(hashUtilsImpl: HashUtilsImpl): HashUtils

    @[Singleton Binds]
    fun bindKeyStorageApi(keyStorageApiImpl: KeyStorageApiImpl): KeyStorageApi

    @[Singleton Binds]
    fun bindEncryptionUtils(encryptionUtilsImpl: EncryptionUtilsImpl): EncryptionUtils

    @[Singleton Binds]
    fun bindEncryptionClientApi(encryptionClientApiImpl: EncryptionClientApiImpl): EncryptionClientApi

    @[Singleton Binds]
    fun bindEncryptedDataStoreApi(encryptedDataStoreApiImpl: EncryptedDataStoreApiImpl): EncryptedDataStoreApi

    companion object {
        @[Singleton Provides]
        fun provideKeyGenerationUtils(keyGenerationUtilsImpl: KeyGenerationUtilsImpl): KeyGenerationUtils {
            return KeyGenerationUtilsImpl.getInstance(keyGenerationUtilsImpl)
        }
    }
}