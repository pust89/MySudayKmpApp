package com.pustovit.mysundaykmpapp.data.repository

import com.pustovit.mysundaykmpapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryCartRepository : CartRepository {

    private val items = MutableStateFlow<List<Product>>(emptyList())

    override fun observeCart(): Flow<List<Product>> = items

    override fun observeIsInCart(productId: Long): Flow<Boolean> =
        items
            .map { list -> list.any { it.id == productId } }
            .distinctUntilChanged()

    override fun add(product: Product) {
        items.update { current ->
            if (current.any { it.id == product.id }) current else current + product
        }
    }

    override fun remove(productId: Long) {
        items.update { current -> current.filterNot { it.id == productId } }
    }
}
