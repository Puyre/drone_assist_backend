package com.drone.assist.features.auth

import ch.qos.logback.core.subst.Token
import com.drone.assist.database.tokens.TokenDto
import com.drone.assist.database.tokens.TokenTable
import com.drone.assist.database.users.UsersTable
import com.drone.assist.features.register.RegisterRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.lang.Exception
import java.util.UUID

class LoginController {

    suspend fun loginUser(call: ApplicationCall) {
        val request = call.receive<LoginRequest>()

        val user = UsersTable.get(request.login)

        if (user == null) {
            call.respond(HttpStatusCode.NotFound, "User not exists")
        } else if (user.password == request.password) {
            val token = UUID.randomUUID().toString()
            TokenTable.create(TokenDto(id = UUID.randomUUID().toString(), token = token, login = user.login))
            call.respond(LoginResponse(token = token))
        } else {
            call.respond(HttpStatusCode.NotFound, "User not exists")
        }

    }
}