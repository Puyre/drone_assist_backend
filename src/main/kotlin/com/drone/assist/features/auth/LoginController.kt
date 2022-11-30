package com.drone.assist.features.auth

import ch.qos.logback.core.subst.Token
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.drone.assist.database.tokens.TokenDto
import com.drone.assist.database.tokens.TokenTable
import com.drone.assist.database.users.UsersTable
import com.drone.assist.features.register.RegisterRequest
import com.drone.assist.features.token.JWTCredentialsProvider
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.lang.Exception
import java.util.*

class LoginController {

    suspend fun loginUser(call: ApplicationCall) {
        val request = call.receive<LoginRequest>()

        val user = UsersTable.get(request.login)

        if (user == null) {
            call.respond(HttpStatusCode.NotFound, "User not exists")
        } else if (user.password == request.password) {

            val token =
                JWT.create()
                    .withAudience(JWTCredentialsProvider.audience)
                    .withIssuer(JWTCredentialsProvider.issuer)
                    .withClaim("username", user.login)
                    .withExpiresAt(Date(System.currentTimeMillis() + 600000))
                    .sign(Algorithm.HMAC256(JWTCredentialsProvider.secret))

            TokenTable.create(TokenDto(id = UUID.randomUUID().toString(), token = token, login = user.login))
            call.respond(LoginResponse(token = token))
        } else {
            call.respond(HttpStatusCode.NotFound, "User not exists")
        }

    }
}