package com.ougi.encryptionimpl.di

import com.ougi.encryptionapi.data.EncryptionClientApi
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyGenerationUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import com.ougi.encryptionimpl.data.utils.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface EncryptionFeatureModule {

    @[Singleton Binds]
    fun bindHashUtils(hashUtilsImpl: HashUtilsImpl): HashUtils

    @[Singleton Binds]
    fun bindKeyStorageUtils(keyStorageUtilsImpl: KeyStorageUtilsImpl): KeyStorageUtils

    @[Singleton Binds]
    fun bindEncryptionUtils(encryptionUtilsImpl: EncryptionUtilsImpl): EncryptionUtils

    @[Singleton Binds]
    fun bindEncryptionClientApi(encryptionClientApiImpl: EncryptionClientApiImpl): EncryptionClientApi

    companion object {
        @[Singleton Provides]
        fun provideKeyGenerationUtils(keyGenerationUtilsImpl: KeyGenerationUtilsImpl): KeyGenerationUtils {
            return KeyGenerationUtilsImpl.getInstance(keyGenerationUtilsImpl)
        }
    }
}