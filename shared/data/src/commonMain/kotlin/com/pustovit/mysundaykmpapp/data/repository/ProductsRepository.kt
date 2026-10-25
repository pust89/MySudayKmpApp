package com.pustovit.mysundaykmpapp.data.repository

import com.pustovit.mysundaykmpapp.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun observeProducts(): Flow<List<Product>>
    fun getProduct(id: Long): Product?
}
