package com.pustovit.mysundaykmpapp.products.details

import com.arkivanov.decompose.ComponentContext

class DefaultProductDetailsComponent(
    componentContext: ComponentContext,
    override val productId: Long,
    private val onFinished: () -> Unit,
) : ProductDetailsComponent, ComponentContext by componentContext {

    override fun onBack() {
        onFinished()
    }
}
