package com.ougi.encryptionimpl.di

import com.ougi.encryptionapi.di.EncryptionFeatureApi
import dagger.Component
import javax.inject.Singleton

@[Component(
    modules = [EncryptionFeatureModule::class],
    dependencies = [EncryptionFeatureDeps::class]
) Singleton]
interface EncryptionFeatureComponent : EncryptionFeatureApi {

    companion object {
        fun newInstance(encryptionFeatureDeps: EncryptionFeatureDeps): EncryptionFeatureApi {
            return DaggerEncryptionFeatureComponent.builder()
                .encryptionFeatureDeps(encryptionFeatureDeps)
                .build()
        }
    }
}