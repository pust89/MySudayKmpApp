package com.pustovit.mysundaykmpapp.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.pustovit.mysundaykmpapp.cart.CartContent
import com.pustovit.mysundaykmpapp.products.root.ProductsRootContent

@Composable
fun RootContent(component: RootComponent) {
    val stack by component.stack.subscribeAsState()
    val active = stack.active.instance

    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = active is RootComponent.Child.Products,
                        onClick = { component.onTabSelected(0) },
                        icon = { Text("П") },
                        label = { Text("Продукты") },
                    )
                    NavigationBarItem(
                        selected = active is RootComponent.Child.Cart,
                        onClick = { component.onTabSelected(1) },
                        icon = { Text("К") },
                        label = { Text("Корзина") },
                    )
                }
            },
        ) { padding ->
            Children(
                stack = component.stack,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.Products -> ProductsRootContent(instance.component)
                    is RootComponent.Child.Cart -> CartContent(instance.component)
                }
            }
        }
    }
}
