package com.pustovit.mysundaykmpapp.data.repository

import com.pustovit.mysundaykmpapp.data.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun observeCart(): Flow<List<Product>>
    fun observeIsInCart(productId: Long): Flow<Boolean>
    fun add(product: Product)
    fun remove(productId: Long)
}
