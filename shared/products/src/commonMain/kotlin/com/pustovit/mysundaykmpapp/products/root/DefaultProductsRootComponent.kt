package com.pustovit.mysundaykmpapp.products.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.pustovit.mysundaykmpapp.products.details.DefaultProductDetailsComponent
import com.pustovit.mysundaykmpapp.products.di.ProductsFeatureComponent
import com.pustovit.mysundaykmpapp.products.list.DefaultProductsListComponent

class DefaultProductsRootComponent(
    componentContext: ComponentContext,
    private val productsFeature: ProductsFeatureComponent,
) : ProductsRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ProductsConfig>()

    override val stack: Value<ChildStack<ProductsConfig, ProductsRootComponent.Child>> = childStack(
        source = navigation,
        serializer = ProductsConfig.serializer(),
        initialConfiguration = ProductsConfig.List,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: ProductsConfig,
        componentContext: ComponentContext,
    ): ProductsRootComponent.Child =
        when (config) {
            ProductsConfig.List -> ProductsRootComponent.Child.List(
                component = DefaultProductsListComponent(
                    componentContext = componentContext,
                    storeFactory = productsFeature.productsListStoreFactory,
                    onProductSelected = { productId ->
                        navigation.pushNew(ProductsConfig.Details(productId))
                    },
                ),
            )
            is ProductsConfig.Details -> ProductsRootComponent.Child.Details(
                component = DefaultProductDetailsComponent(
                    componentContext = componentContext,
                    storeFactory = productsFeature.productDetailsStoreFactory,
                    productId = config.productId,
                    onFinished = { navigation.pop() },
                ),
            )
        }
}
