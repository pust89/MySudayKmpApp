package com.pustovit.mysundaykmpapp.products.list

import kotlinx.coroutines.flow.StateFlow

interface ProductsListComponent {
    val state: StateFlow<ProductsListStore.State>
    fun onIntent(intent: ProductsListStore.Intent)
}
