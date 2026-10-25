package com.pustovit.mysundaykmpapp.products.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.pustovit.mysundaykmpapp.products.details.ProductDetailsContent
import com.pustovit.mysundaykmpapp.products.list.ProductsListContent

@Composable
fun ProductsRootContent(component: ProductsRootComponent) {
    Children(stack = component.stack) { child ->
        when (val instance = child.instance) {
            is ProductsRootComponent.Child.List -> ProductsListContent(instance.component)
            is ProductsRootComponent.Child.Details -> ProductDetailsContent(instance.component)
        }
    }
}
