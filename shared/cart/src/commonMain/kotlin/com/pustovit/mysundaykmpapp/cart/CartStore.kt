package com.pustovit.mysundaykmpapp.cart

import com.arkivanov.mvikotlin.core.store.Store
import com.pustovit.mysundaykmpapp.data.model.Product

interface CartStore : Store<
    CartStore.Intent,
    CartStore.State,
    CartStore.Label,
    > {

    sealed interface Intent {
        data class Remove(val productId: Long) : Intent
    }

    data class State(
        val items: List<Product> = emptyList(),
    )

    sealed interface Label
}
