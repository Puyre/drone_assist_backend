package com.drone.assist.database.tournaments

import com.drone.assist.database.tokens.TokenDto
import com.drone.assist.database.tokens.TokenTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object TournamentsTable : Table("tournaments") {

    private val id = TournamentsTable.varchar(name = "id", length = 50)
    private val name = TournamentsTable.varchar(name = "name", length = 50)
    private val hasStarted = TournamentsTable.bool(name = "has_started")
    private val teamsCount = TournamentsTable.integer(name = "teams_count").nullable()
    private val isCompleted = TournamentsTable.bool(name = "is_completed")
    private val hostUserId = TournamentsTable.varchar(name = "host_user_id", length = 50)

    fun create(tournamentDto: TournamentDto) {
        transaction {
            TournamentsTable.insert {
                it[id] = tournamentDto.id
                it[name] = tournamentDto.name
                it[hasStarted] = tournamentDto.hasStarted
                it[teamsCount] = tournamentDto.teamsCount
                it[isCompleted] = tournamentDto.isCompleted
                it[hostUserId] = tournamentDto.hostUserId
            }
        }
    }

    fun get(hostUserId: String): List<TournamentDto> {
        return transaction {
            TournamentsTable.select {
                TournamentsTable.hostUserId eq hostUserId
            }.map {
                TournamentDto(
                    id = it[TournamentsTable.id],
                    name = it[name],
                    hasStarted = it[hasStarted],
                    teamsCount = it[teamsCount],
                    isCompleted = it[isCompleted],
                    hostUserId = it[TournamentsTable.hostUserId],
                )
            }
        }
    }
}