package com.pustovit.mysundaykmpapp.products.details

import kotlinx.coroutines.flow.StateFlow

interface ProductDetailsComponent {
    val state: StateFlow<ProductDetailsStore.State>
    fun onIntent(intent: ProductDetailsStore.Intent)
    fun onBack()
}
