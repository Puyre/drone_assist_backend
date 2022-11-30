package com.drone.assist.features.token

import io.ktor.server.application.*

object JWTCredentialsProvider {

    lateinit var secret: String
    lateinit var issuer: String
    lateinit var audience: String
    lateinit var realm: String

    fun init(environment: ApplicationEnvironment) {
        secret = environment.config.property("ktor.jwt.secret").getString()
        issuer = environment.config.property("ktor.jwt.issuer").getString()
        audience = environment.config.property("ktor.jwt.audience").getString()
        realm = environment.config.property("ktor.jwt.realm").getString()
    }
}