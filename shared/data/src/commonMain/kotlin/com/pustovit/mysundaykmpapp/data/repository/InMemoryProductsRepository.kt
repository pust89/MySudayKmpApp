package com.pustovit.mysundaykmpapp.data.repository

import com.pustovit.mysundaykmpapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class InMemoryProductsRepository : ProductsRepository {

    private val products = listOf(
        Product(id = 1, name = "Кофе", price = 4.50),
        Product(id = 2, name = "Чай", price = 3.20),
        Product(id = 3, name = "Круассан", price = 2.80),
        Product(id = 4, name = "Сэндвич", price = 6.90),
        Product(id = 5, name = "Сок", price = 2.50),
        Product(id = 6, name = "Печенье", price = 1.80),
        Product(id = 7, name = "Йогурт", price = 3.40),
        Product(id = 8, name = "Вода", price = 1.20),
    )

    override fun observeProducts(): Flow<List<Product>> = flowOf(products)

    override fun getProduct(id: Long): Product? = products.firstOrNull { it.id == id }
}
