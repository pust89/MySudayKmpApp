package com.pustovit.mysundaykmpapp.cart

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CartContent(@Suppress("UNUSED_PARAMETER") component: CartComponent) {
    Text(
        text = "Корзина",
        modifier = Modifier.padding(16.dp),
    )
}
