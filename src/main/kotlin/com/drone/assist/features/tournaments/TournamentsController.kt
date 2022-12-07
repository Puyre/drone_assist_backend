package com.drone.assist.features.tournaments

import com.drone.assist.database.tournaments.TournamentDto
import com.drone.assist.database.tournaments.TournamentsTable
import com.drone.assist.database.users.UsersTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.UUID

class TournamentsController {

    suspend fun createTournament(call: ApplicationCall) {
        val request = call.receive<CreatTournamentRequest>()
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()

        val tournamentDto = TournamentDto(
            id = UUID.randomUUID().toString(),
            name = request.name,
            hostUserId = username,
            hasStarted = false,
            teamsCount = request.teamsCount,
            isCompleted = false
        )

        TournamentsTable.create(tournamentDto)

        call.respond(tournamentDto)
    }

    suspend fun getTournaments(call: ApplicationCall) {
        val principal = call.principal<JWTPrincipal>()
        val username = principal!!.payload.getClaim("username").asString()

        val user = UsersTable.get(username)

        if (user != null) {
            val tournaments = TournamentsTable.get(hostUserId = user.login)
            call.respond(tournaments)
        } else {
            call.respond(HttpStatusCode.BadRequest, message = "User not exists")
        }
    }
}