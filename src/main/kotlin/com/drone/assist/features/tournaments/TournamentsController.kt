package com.drone.assist.features.tournaments

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class TournamentsController {

    suspend fun createTournament(call: ApplicationCall) {
        //val request = call.receive<CreatTournamentRequest>()
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()
        val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
        call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
    }
}