package com.pustovit.mysundaykmpapp.products.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.pustovit.mysundaykmpapp.data.model.Product
import com.pustovit.mysundaykmpapp.data.repository.CartRepository
import com.pustovit.mysundaykmpapp.data.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class ProductDetailsStoreFactory(
    private val storeFactory: StoreFactory,
    private val productsRepository: ProductsRepository,
    private val cartRepository: CartRepository,
) {
    fun create(productId: Long): ProductDetailsStore =
        object : ProductDetailsStore, Store<ProductDetailsStore.Intent, ProductDetailsStore.State, ProductDetailsStore.Label> by storeFactory.create(
            name = "ProductDetailsStore",
            initialState = ProductDetailsStore.State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(productId) },
            reducer = ReducerImpl,
        ) {}

    private sealed interface Action {
        data object Init : Action
    }

    private sealed interface Message {
        data class SetProduct(val product: Product?) : Message
        data class SetInCart(val isInCart: Boolean) : Message
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>(mainContext = Dispatchers.Main) {
        override fun invoke() {
            dispatch(Action.Init)
        }
    }

    private inner class ExecutorImpl(
        private val productId: Long,
    ) : CoroutineExecutor<
        ProductDetailsStore.Intent,
        Action,
        ProductDetailsStore.State,
        Message,
        ProductDetailsStore.Label,
        >(mainContext = Dispatchers.Main) {

        override fun executeAction(action: Action) {
            when (action) {
                Action.Init -> {
                    dispatch(Message.SetProduct(productsRepository.getProduct(productId)))
                    scope.launch {
                        cartRepository.observeIsInCart(productId).collect { isInCart ->
                            dispatch(Message.SetInCart(isInCart))
                        }
                    }
                }
            }
        }

        override fun executeIntent(intent: ProductDetailsStore.Intent) {
            when (intent) {
                ProductDetailsStore.Intent.AddToCart ->
                    state().product?.let(cartRepository::add)
                ProductDetailsStore.Intent.RemoveFromCart ->
                    cartRepository.remove(productId)
            }
        }
    }

    private object ReducerImpl : Reducer<ProductDetailsStore.State, Message> {
        override fun ProductDetailsStore.State.reduce(msg: Message): ProductDetailsStore.State =
            when (msg) {
                is Message.SetProduct -> copy(product = msg.product)
                is Message.SetInCart -> copy(isInCart = msg.isInCart)
            }
    }
}
