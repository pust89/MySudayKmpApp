package com.pustovit.mysundaykmpapp.products.root

import kotlinx.serialization.Serializable

@Serializable
sealed interface ProductsConfig {
    @Serializable
    data object List : ProductsConfig

    @Serializable
    data class Details(val productId: Long) : ProductsConfig
}
