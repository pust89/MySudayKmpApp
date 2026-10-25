package com.pustovit.mysundaykmpapp.cart

import kotlinx.coroutines.flow.StateFlow

interface CartComponent {
    val state: StateFlow<CartStore.State>
    fun onIntent(intent: CartStore.Intent)
}
