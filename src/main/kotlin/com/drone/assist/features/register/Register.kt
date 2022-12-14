package com.drone.assist.features.register

data class RegisterRequest(
    val login: String,
    val password: String,
)

data class RegisterResponse(
    val accessToken: String,
    val refreshToken: String,
)
