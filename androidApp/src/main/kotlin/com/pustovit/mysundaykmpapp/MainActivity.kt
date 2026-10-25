package com.pustovit.mysundaykmpapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.pustovit.mysundaykmpapp.di.AppComponent
import com.pustovit.mysundaykmpapp.di.create
import com.pustovit.mysundaykmpapp.root.DefaultRootComponent
import com.pustovit.mysundaykmpapp.root.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val root = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            appComponent = AppComponent::class.create(),
        )

        setContent {
            RootContent(root)
        }
    }
}
