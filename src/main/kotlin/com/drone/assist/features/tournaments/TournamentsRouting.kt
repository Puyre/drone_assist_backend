package com.drone.assist.features.tournaments

import com.drone.assist.features.register.RegisterController
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureTournamentsRouting() {
    val registerController = TournamentsController()
    routing {
        authenticate {
            post("/tournaments/create") {
                registerController.createTournament(call)
            }
            get("/tournaments/get") {
                registerController.getTournaments(call)
            }
        }
    }
}