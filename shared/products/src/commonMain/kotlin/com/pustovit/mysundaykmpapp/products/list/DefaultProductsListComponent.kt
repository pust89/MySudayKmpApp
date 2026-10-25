package com.pustovit.mysundaykmpapp.products.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultProductsListComponent(
    componentContext: ComponentContext,
    storeFactory: ProductsListStoreFactory,
    private val onProductSelected: (Long) -> Unit,
) : ProductsListComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getOrCreate {
        StoreHolder(storeFactory.create())
    }.store

    override val state: StateFlow<ProductsListStore.State> =
        store.stateFlow(scope = coroutineScope())

    override fun onIntent(intent: ProductsListStore.Intent) = store.accept(intent)

    init {
        coroutineScope().launch {
            store.labels.collect { label ->
                when (label) {
                    is ProductsListStore.Label.OpenDetails ->
                        onProductSelected(label.productId)
                }
            }
        }
    }

    private class StoreHolder(
        val store: ProductsListStore,
    ) : InstanceKeeper.Instance {
        override fun onDestroy() {
            store.dispose()
        }
    }
}
