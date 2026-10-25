package com.pustovit.mysundaykmpapp.cart

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow

class DefaultCartComponent(
    componentContext: ComponentContext,
    storeFactory: CartStoreFactory,
) : CartComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getOrCreate {
        StoreHolder(storeFactory.create())
    }.store

    override val state: StateFlow<CartStore.State> =
        store.stateFlow(scope = coroutineScope())

    override fun onIntent(intent: CartStore.Intent) = store.accept(intent)

    private class StoreHolder(
        val store: CartStore,
    ) : InstanceKeeper.Instance {
        override fun onDestroy() {
            store.dispose()
        }
    }
}
