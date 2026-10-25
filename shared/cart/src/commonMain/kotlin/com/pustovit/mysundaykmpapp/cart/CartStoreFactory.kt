package com.pustovit.mysundaykmpapp.cart

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.pustovit.mysundaykmpapp.data.model.Product
import com.pustovit.mysundaykmpapp.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CartStoreFactory(
    private val storeFactory: StoreFactory,
    private val cartRepository: CartRepository,
) {
    fun create(): CartStore =
        object : CartStore, Store<CartStore.Intent, CartStore.State, CartStore.Label> by storeFactory.create(
            name = "CartStore",
            initialState = CartStore.State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private sealed interface Action {
        data object Init : Action
    }

    private sealed interface Message {
        data class SetItems(val items: List<Product>) : Message
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>(mainContext = Dispatchers.Main) {
        override fun invoke() {
            dispatch(Action.Init)
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<
        CartStore.Intent,
        Action,
        CartStore.State,
        Message,
        CartStore.Label,
        >(mainContext = Dispatchers.Main) {

        override fun executeAction(action: Action) {
            when (action) {
                Action.Init ->
                    scope.launch {
                        cartRepository.observeCart().collect { items ->
                            dispatch(Message.SetItems(items))
                        }
                    }
            }
        }

        override fun executeIntent(intent: CartStore.Intent) {
            when (intent) {
                is CartStore.Intent.Remove ->
                    cartRepository.remove(intent.productId)
            }
        }
    }

    private object ReducerImpl : Reducer<CartStore.State, Message> {
        override fun CartStore.State.reduce(msg: Message): CartStore.State =
            when (msg) {
                is Message.SetItems -> copy(items = msg.items)
            }
    }
}
