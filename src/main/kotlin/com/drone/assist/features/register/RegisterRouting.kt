package com.drone.assist.features.register

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureRegisterRouting() {
    val registerController = RegisterController()
    routing {
        post("/register") {
            registerController.registerUser(call)
        }
    }
}