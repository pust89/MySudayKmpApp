package com.pustovit.mysundaykmpapp.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.pustovit.mysundaykmpapp.data.repository.CartRepository
import com.pustovit.mysundaykmpapp.data.repository.InMemoryCartRepository
import com.pustovit.mysundaykmpapp.data.repository.InMemoryProductsRepository
import com.pustovit.mysundaykmpapp.data.repository.ProductsRepository
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@AppScope
abstract class AppComponent {

    abstract val productsRepository: ProductsRepository
    abstract val cartRepository: CartRepository
    abstract val storeFactory: StoreFactory

    @Provides
    @AppScope
    fun productsRepository(): ProductsRepository = InMemoryProductsRepository()

    @Provides
    @AppScope
    fun cartRepository(): CartRepository = InMemoryCartRepository()

    @Provides
    @AppScope
    fun storeFactory(): StoreFactory = DefaultStoreFactory()
}
