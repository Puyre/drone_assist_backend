package com.drone.assist

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.drone.assist.features.auth.configureLoginRouting
import com.drone.assist.features.register.configureRegisterRouting
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.util.logging.*
import org.jetbrains.exposed.sql.Database
import org.slf4j.event.Level

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
        install(CallLogging){
            level = Level.TRACE
        }
        configureLoginRouting()
        configureRegisterRouting()
    }.start(wait = true)
}
