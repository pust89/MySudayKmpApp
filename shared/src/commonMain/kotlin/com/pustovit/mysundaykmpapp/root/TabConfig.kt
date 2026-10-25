package com.pustovit.mysundaykmpapp.root

import kotlinx.serialization.Serializable

@Serializable
sealed interface TabConfig {
    @Serializable
    data object Products : TabConfig

    @Serializable
    data object Cart : TabConfig
}
