package com.ougi.encryptionimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.encryptionapi.di.EncryptionFeatureApi
import dagger.Component

@[Component(
    modules = [EncryptionFeatureModule::class],
    dependencies = [EncryptionFeatureDeps::class]
) Feature]
interface EncryptionFeatureComponent : EncryptionFeatureApi {
    companion object {
        fun newInstance(encryptionFeatureDeps: EncryptionFeatureDeps): EncryptionFeatureApi {
            return DaggerEncryptionFeatureComponent.builder()
                .encryptionFeatureDeps(encryptionFeatureDeps)
                .build()
        }
    }
}