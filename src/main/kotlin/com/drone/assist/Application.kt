package com.drone.assist

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.drone.assist.features.auth.configureLoginRouting
import com.drone.assist.features.register.configureRegisterRouting
import com.drone.assist.features.token.JWTCredentialsProvider
import com.drone.assist.features.tournaments.configureTournamentsRouting
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
    JWTCredentialsProvider.init(environment)
    install(ContentNegotiation) {
        gson()
    }
    install(CallLogging) {
        level = Level.TRACE
    }
    authentication {
        jwt {
            realm = JWTCredentialsProvider.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JWTCredentialsProvider.secret))
                    .withAudience(JWTCredentialsProvider.audience)
                    .withIssuer(JWTCredentialsProvider.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
    configureLoginRouting()
    configureRegisterRouting()
    configureTournamentsRouting()
}
