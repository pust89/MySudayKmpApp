package com.pustovit.mysundaykmpapp.products.list

import com.arkivanov.mvikotlin.core.store.Store
import com.pustovit.mysundaykmpapp.data.model.Product

interface ProductsListStore : Store<
    ProductsListStore.Intent,
    ProductsListStore.State,
    ProductsListStore.Label,
    > {

    sealed interface Intent {
        data class ProductClicked(val productId: Long) : Intent
    }

    data class State(
        val products: List<Product> = emptyList(),
    )

    sealed interface Label {
        data class OpenDetails(val productId: Long) : Label
    }
}
