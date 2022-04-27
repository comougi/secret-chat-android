package com.ougi.coreutils.dagger

import com.ougi.corecommon.base.di.BaseFeatureApi
import com.ougi.corecommon.base.di.BaseFeatureDeps
import java.lang.ref.WeakReference

class ComponentHolderDelegate<A : BaseFeatureApi, D : BaseFeatureDeps>(
    private val apiFactory: (D) -> A
) : ComponentHolder<A, D> {
    override var depsProvider: (() -> D)? = null

    private var component: WeakReference<A>? = null

    private fun apiInstance(): A {
        var api: A? = null

        synchronized(this) {
            depsProvider?.let { provider ->
                api = component?.get()
                if (api == null) {
                    api = apiFactory(provider.invoke())
                        .also { component = WeakReference(api) }
                }
            }
        }

        return requireNotNull(api) { "Not initialized" }
    }

    override fun getInstance(): A = apiInstance()
}