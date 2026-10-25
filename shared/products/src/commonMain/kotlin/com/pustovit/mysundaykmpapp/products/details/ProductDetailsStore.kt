package com.pustovit.mysundaykmpapp.products.details

import com.arkivanov.mvikotlin.core.store.Store
import com.pustovit.mysundaykmpapp.data.model.Product

interface ProductDetailsStore : Store<
    ProductDetailsStore.Intent,
    ProductDetailsStore.State,
    ProductDetailsStore.Label,
    > {

    sealed interface Intent {
        data object AddToCart : Intent
        data object RemoveFromCart : Intent
    }

    data class State(
        val product: Product? = null,
        val isInCart: Boolean = false,
    )

    sealed interface Label
}
