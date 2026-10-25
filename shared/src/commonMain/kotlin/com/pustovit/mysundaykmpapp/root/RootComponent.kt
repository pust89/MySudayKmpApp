package com.pustovit.mysundaykmpapp.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.pustovit.mysundaykmpapp.cart.CartComponent
import com.pustovit.mysundaykmpapp.products.root.ProductsRootComponent

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>
    fun onTabSelected(index: Int)

    sealed interface Child {
        class Products(val component: ProductsRootComponent) : Child
        class Cart(val component: CartComponent) : Child
    }
}
