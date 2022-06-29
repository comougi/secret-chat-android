package com.ougi.encryptionimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.encryptionapi.di.EncryptionFeatureApi

object EncryptionFeatureComponentHolder :
    ComponentHolder<EncryptionFeatureApi, EncryptionFeatureDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<EncryptionFeatureApi, EncryptionFeatureDeps> { deps ->
            EncryptionFeatureComponent.newInstance(deps)
        }

    override var depsProvider: (() -> EncryptionFeatureDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): EncryptionFeatureComponent =
        componentHolderDelegate.getInstance() as EncryptionFeatureComponent
}