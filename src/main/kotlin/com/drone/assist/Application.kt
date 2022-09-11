package com.drone.assist

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.drone.assist.features.auth.configureLoginRouting
import com.drone.assist.features.register.configureRegisterRouting
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        password = "",
        user = "postgres"
    )
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            gson()
        }
        configureLoginRouting()
        configureRegisterRouting()
    }.start(wait = true)
}
