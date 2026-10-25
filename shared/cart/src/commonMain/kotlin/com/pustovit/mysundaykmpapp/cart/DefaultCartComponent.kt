package com.pustovit.mysundaykmpapp.cart

import com.arkivanov.decompose.ComponentContext

class DefaultCartComponent(
    componentContext: ComponentContext,
) : CartComponent, ComponentContext by componentContext
