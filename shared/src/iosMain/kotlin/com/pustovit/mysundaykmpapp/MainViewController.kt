package com.pustovit.mysundaykmpapp

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.pustovit.mysundaykmpapp.di.createAppComponent
import com.pustovit.mysundaykmpapp.root.DefaultRootComponent
import com.pustovit.mysundaykmpapp.root.RootContent
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val lifecycle = ApplicationLifecycle()
    // create() is KSP output on each iOS target, so iosMain cannot call it directly.
    val root = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        appComponent = createAppComponent(),
    )
    return ComposeUIViewController {
        RootContent(root)
    }
}
