package com.pustovit.mysundaykmpapp.cart.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.pustovit.mysundaykmpapp.cart.CartStoreFactory
import com.pustovit.mysundaykmpapp.data.repository.CartRepository
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import me.tatarka.inject.annotations.Provides

/**
 * Bindings from [com.pustovit.mysundaykmpapp.di.AppComponent] are passed in here.
 * This module cannot depend on `:shared` (where that component lives) without a Gradle cycle.
 */
@Component
abstract class CartFeatureComponent(
    @get:Provides protected val cartRepository: CartRepository,
    @get:Provides protected val storeFactory: StoreFactory,
) {
    abstract val cartStoreFactory: CartStoreFactory
}

@KmpComponentCreate
expect fun createCartFeatureComponent(
    cartRepository: CartRepository,
    storeFactory: StoreFactory,
): CartFeatureComponent
