package com.drone.assist

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.drone.assist.features.auth.configureLoginRouting
import com.drone.assist.features.register.configureRegisterRouting
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

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        password = environment.config.propertyOrNull("ktor.environment.dbPassword").toString(),
        user = "postgres"
    )
    install(ContentNegotiation) {
        gson()
    }
    install(CallLogging){
        level = Level.TRACE
    }
    configureLoginRouting()
    configureRegisterRouting()
}
