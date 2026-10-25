package com.pustovit.mysundaykmpapp.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.pustovit.mysundaykmpapp.cart.DefaultCartComponent
import com.pustovit.mysundaykmpapp.di.AppComponent
import com.pustovit.mysundaykmpapp.products.di.createProductsFeatureComponent
import com.pustovit.mysundaykmpapp.products.root.DefaultProductsRootComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
    appComponent: AppComponent,
) : RootComponent, ComponentContext by componentContext {

    private val productsFeature = createProductsFeatureComponent(
        productsRepository = appComponent.productsRepository,
        cartRepository = appComponent.cartRepository,
        storeFactory = appComponent.storeFactory,
    )

    private val navigation = StackNavigation<TabConfig>()

    override val stack: Value<ChildStack<TabConfig, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = TabConfig.serializer(),
        initialConfiguration = TabConfig.Products,
        handleBackButton = true,
        childFactory = ::child,
    )

    override fun onTabSelected(index: Int) {
        navigation.bringToFront(
            when (index) {
                0 -> TabConfig.Products
                else -> TabConfig.Cart
            },
        )
    }

    private fun child(
        config: TabConfig,
        componentContext: ComponentContext,
    ): RootComponent.Child =
        when (config) {
            TabConfig.Products -> RootComponent.Child.Products(
                component = DefaultProductsRootComponent(
                    componentContext = componentContext,
                    productsFeature = productsFeature,
                ),
            )
            TabConfig.Cart -> RootComponent.Child.Cart(
                component = DefaultCartComponent(
                    componentContext = componentContext,
                ),
            )
        }
}
