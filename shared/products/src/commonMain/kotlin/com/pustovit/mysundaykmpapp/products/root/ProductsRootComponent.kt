package com.pustovit.mysundaykmpapp.products.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.pustovit.mysundaykmpapp.products.details.ProductDetailsComponent
import com.pustovit.mysundaykmpapp.products.list.ProductsListComponent

interface ProductsRootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class List(val component: ProductsListComponent) : Child
        class Details(val component: ProductDetailsComponent) : Child
    }
}
