package com.pustovit.mysundaykmpapp.products.list

import com.arkivanov.decompose.ComponentContext

class DefaultProductsListComponent(
    componentContext: ComponentContext,
    private val onProductSelected: (Long) -> Unit,
) : ProductsListComponent, ComponentContext by componentContext {

    override fun onProductClicked(productId: Long) {
        onProductSelected(productId)
    }
}
