package com.pustovit.mysundaykmpapp.products.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.pustovit.mysundaykmpapp.data.repository.CartRepository
import com.pustovit.mysundaykmpapp.data.repository.ProductsRepository
import com.pustovit.mysundaykmpapp.products.details.ProductDetailsStoreFactory
import com.pustovit.mysundaykmpapp.products.list.ProductsListStoreFactory
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import me.tatarka.inject.annotations.Provides

/**
 * Bindings from [com.pustovit.mysundaykmpapp.di.AppComponent] are passed in here.
 * This module cannot depend on `:shared` (where that component lives) without a Gradle cycle.
 */
@Component
abstract class ProductsFeatureComponent(
    @get:Provides protected val productsRepository: ProductsRepository,
    @get:Provides protected val cartRepository: CartRepository,
    @get:Provides protected val storeFactory: StoreFactory,
) {
    abstract val productsListStoreFactory: ProductsListStoreFactory
    abstract val productDetailsStoreFactory: ProductDetailsStoreFactory
}

@KmpComponentCreate
expect fun createProductsFeatureComponent(
    productsRepository: ProductsRepository,
    cartRepository: CartRepository,
    storeFactory: StoreFactory,
): ProductsFeatureComponent
