package com.ougi.datastoreimpl.di

import com.ougi.coreutils.dagger.ComponentHolder
import com.ougi.coreutils.dagger.ComponentHolderDelegate
import com.ougi.datastoreapi.di.DataStoreFeatureApi

object DataStoreFeatureComponentHolder :
    ComponentHolder<DataStoreFeatureApi, DataStoreFeatureDeps> {

    private val componentHolderDelegate =
        ComponentHolderDelegate<DataStoreFeatureApi, DataStoreFeatureDeps> { deps ->
            DataStoreFeatureComponent.newInstance(deps)
        }

    override var depsProvider: (() -> DataStoreFeatureDeps)? by componentHolderDelegate::depsProvider

    override fun getInstance(): DataStoreFeatureComponent =
        componentHolderDelegate.getInstance() as DataStoreFeatureComponent

}