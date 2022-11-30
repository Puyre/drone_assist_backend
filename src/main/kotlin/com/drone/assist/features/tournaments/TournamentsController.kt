package com.drone.assist.features.tournaments

import io.ktor.server.application.*
import io.ktor.server.request.*

class TournamentsController {

    suspend fun createTournament(call: ApplicationCall) {
        val request = call.receive<CreatTournamentRequest>()

    }
}