package com.ougi.datastoreimpl.di

import com.ougi.coreutils.dagger.Feature
import com.ougi.datastoreapi.di.DataStoreFeatureApi
import dagger.Component

@[Component(
    modules = [DataStoreFeatureModule::class],
    dependencies = [DataStoreFeatureDeps::class]
) Feature]
interface DataStoreFeatureComponent : DataStoreFeatureApi {
    companion object {
        fun newInstance(dataStoreFeatureDeps: DataStoreFeatureDeps): DataStoreFeatureApi {
            return DaggerDataStoreFeatureComponent.builder()
                .dataStoreFeatureDeps(dataStoreFeatureDeps)
                .build()
        }
    }
}