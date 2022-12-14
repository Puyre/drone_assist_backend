package com.drone.assist.features.register

import com.drone.assist.features.register.RegisterController
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*


fun Application.configureRegisterRouting() {
    val registerController = RegisterController()
    routing {
        post("/signup") {
            registerController.registerUser(call)
        }
    }
}