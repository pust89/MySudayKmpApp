package com.pustovit.mysundaykmpapp.products.list

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.pustovit.mysundaykmpapp.data.model.Product
import com.pustovit.mysundaykmpapp.data.repository.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class ProductsListStoreFactory(
    private val storeFactory: StoreFactory,
    private val productsRepository: ProductsRepository,
) {
    fun create(): ProductsListStore =
        object : ProductsListStore, Store<ProductsListStore.Intent, ProductsListStore.State, ProductsListStore.Label> by storeFactory.create(
            name = "ProductsListStore",
            initialState = ProductsListStore.State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ) {}

    private sealed interface Action {
        data object Init : Action
    }

    private sealed interface Message {
        data class SetProducts(val products: List<Product>) : Message
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>(mainContext = Dispatchers.Main) {
        override fun invoke() {
            dispatch(Action.Init)
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<
        ProductsListStore.Intent,
        Action,
        ProductsListStore.State,
        Message,
        ProductsListStore.Label,
        >(mainContext = Dispatchers.Main) {

        override fun executeAction(action: Action) {
            when (action) {
                Action.Init ->
                    scope.launch {
                        productsRepository.observeProducts().collect { products ->
                            dispatch(Message.SetProducts(products))
                        }
                    }
            }
        }

        override fun executeIntent(intent: ProductsListStore.Intent) {
            when (intent) {
                is ProductsListStore.Intent.ProductClicked ->
                    publish(ProductsListStore.Label.OpenDetails(intent.productId))
            }
        }
    }

    private object ReducerImpl : Reducer<ProductsListStore.State, Message> {
        override fun ProductsListStore.State.reduce(msg: Message): ProductsListStore.State =
            when (msg) {
                is Message.SetProducts -> copy(products = msg.products)
            }
    }
}
