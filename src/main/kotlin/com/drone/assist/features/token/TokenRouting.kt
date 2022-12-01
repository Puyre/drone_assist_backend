package com.drone.assist.features.token

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureTokenRouting() {
    val tokenController = TokenController()
    routing {
        post("/refresh") {
            tokenController.refreshToken(call)
        }
    }
}