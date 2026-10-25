package com.pustovit.mysundaykmpapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform