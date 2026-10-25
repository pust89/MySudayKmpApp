package com.pustovit.mysundaykmpapp.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import com.pustovit.mysundaykmpapp.data.model.Product
import kotlin.math.roundToInt

@Composable
fun CartContent(component: CartComponent) {
    val state by component.state.collectAsState()
    CartContent(
        state = state,
        onIntent = component::onIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartStore.State,
    onIntent: (CartStore.Intent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Корзина") })
        },
    ) { padding ->
        if (state.items.isEmpty()) {
            Text(
                text = "Корзина пуста",
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = state.items,
                    key = { product -> product.id },
                ) { product ->
                    CartItemCard(
                        product = product,
                        onRemove = {
                            onIntent(CartStore.Intent.Remove(product.id))
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun CartItemCard(
    product: Product,
    onRemove: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(text = formatPrice(product.price))
            TextButton(onClick = onRemove) {
                Text("Удалить")
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
