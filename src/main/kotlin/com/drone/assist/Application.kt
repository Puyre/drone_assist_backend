package com.drone.assist

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.drone.assist.features.auth.configureLoginRouting
import com.drone.assist.features.register.configureRegisterRouting
import com.drone.assist.features.token.JWTCredentialsProvider
import com.drone.assist.features.token.configureAuth
import com.drone.assist.features.token.configureTokenRouting
import com.drone.assist.features.tournaments.configureTournamentsRouting
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import io.ktor.util.logging.*
import org.jetbrains.exposed.sql.Database
import org.slf4j.event.Level

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    val config = HikariConfig("hikari.properties")
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)
    JWTCredentialsProvider.init(environment)
    install(ContentNegotiation) {
        gson()
    }
    install(CallLogging) {
        level = Level.TRACE
    }
    configureAuth()
    configureLoginRouting()
    configureRegisterRouting()
    configureTournamentsRouting()
    configureLoginRouting()
    configureTokenRouting()
}
