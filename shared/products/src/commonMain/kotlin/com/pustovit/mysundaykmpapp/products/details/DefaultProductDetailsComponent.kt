package com.pustovit.mysundaykmpapp.products.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow

class DefaultProductDetailsComponent(
    componentContext: ComponentContext,
    storeFactory: ProductDetailsStoreFactory,
    productId: Long,
    private val onFinished: () -> Unit,
) : ProductDetailsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getOrCreate {
        StoreHolder(storeFactory.create(productId))
    }.store

    override val state: StateFlow<ProductDetailsStore.State> =
        store.stateFlow(scope = coroutineScope())

    override fun onIntent(intent: ProductDetailsStore.Intent) = store.accept(intent)

    override fun onBack() {
        onFinished()
    }

    private class StoreHolder(
        val store: ProductDetailsStore,
    ) : InstanceKeeper.Instance {
        override fun onDestroy() {
            store.dispose()
        }
    }
}
