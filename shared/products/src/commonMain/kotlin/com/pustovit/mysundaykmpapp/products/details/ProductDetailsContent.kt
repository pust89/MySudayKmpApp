package com.pustovit.mysundaykmpapp.products.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ProductDetailsContent(component: ProductDetailsComponent) {
    val state by component.state.collectAsState()
    ProductDetailsContent(
        state = state,
        onIntent = component::onIntent,
        onBack = component::onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsContent(
    state: ProductDetailsStore.State,
    onIntent: (ProductDetailsStore.Intent) -> Unit,
    onBack: () -> Unit,
) {
    val product = state.product

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.name ?: "") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Назад")
                    }
                },
            )
        },
    ) { padding ->
        if (product == null) {
            Text(
                text = "Товар не найден",
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(text = formatPrice(product.price))
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        onIntent(
                            if (state.isInCart) {
                                ProductDetailsStore.Intent.RemoveFromCart
                            } else {
                                ProductDetailsStore.Intent.AddToCart
                            },
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = if (state.isInCart) {
                            "Удалить из корзины"
                        } else {
                            "Добавить в корзину"
                        },
                    )
                }
            }
        }
    }
}

private fun formatPrice(price: Double): String {
    val cents = (price * 100).roundToInt()
    val whole = cents / 100
    val fraction = (cents % 100).toString().padStart(2, '0')
    return "$whole.$fraction"
}
