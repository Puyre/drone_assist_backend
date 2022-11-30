package com.drone.assist.database.tournaments

data class TournamentDto (
    val id: String,
    val name: String,
    val hostUserId: String,
    val hasStarted: Boolean,
    val teamsCount: Int?,
    val isCompleted: Boolean,
)