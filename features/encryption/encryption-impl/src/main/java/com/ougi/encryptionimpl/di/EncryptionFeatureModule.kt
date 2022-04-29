package com.ougi.encryptionimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.encryptionapi.data.utils.EncryptionUtils
import com.ougi.encryptionapi.data.utils.HashUtils
import com.ougi.encryptionapi.data.utils.KeyStorageUtils
import com.ougi.encryptionapi.data.utils.KeyUtils
import com.ougi.encryptionimpl.data.utils.EncryptionUtilsImpl
import com.ougi.encryptionimpl.data.utils.HashUtilsImpl
import com.ougi.encryptionimpl.data.utils.KeyStorageUtilsImpl
import com.ougi.encryptionimpl.data.utils.KeyUtilsImpl
import dagger.Binds
import dagger.Module

@Module
interface EncryptionFeatureModule {

    @[Feature Binds]
    fun bindHashUtils(hashUtilsImpl: HashUtilsImpl): HashUtils


    @[Feature Binds]
    fun bindKeyUtils(keyUtilsImpl: KeyUtilsImpl): KeyUtils

    @[Feature Binds]
    fun bindKeyStorageUtils(keyStorageUtilsImpl: KeyStorageUtilsImpl): KeyStorageUtils

    @[Feature Binds]
    fun bindEncryptionUtils(encryptionUtilsImpl: EncryptionUtilsImpl): EncryptionUtils
}