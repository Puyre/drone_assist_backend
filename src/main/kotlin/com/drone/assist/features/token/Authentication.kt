package com.drone.assist.features.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuth() {
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
}