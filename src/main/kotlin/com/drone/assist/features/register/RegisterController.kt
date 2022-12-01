package com.drone.assist.features.register

import com.drone.assist.database.tokens.TokenDto
import com.drone.assist.database.tokens.TokenTable
import com.drone.assist.database.users.UserDto
import com.drone.assist.database.users.UsersTable
import com.drone.assist.features.auth.LoginResponse
import com.drone.assist.features.token.generateTokenPair
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.UUID

class RegisterController {

    suspend fun registerUser(call: ApplicationCall) {
        val request = call.receive<RegisterRequest>()

        val user = UsersTable.get(request.login)

        if (user != null) {
            call.respond(HttpStatusCode.Conflict, message = "User already exists")
        } else {
            val tokenPair = generateTokenPair(userLogin = request.login)
            UsersTable.create(UserDto(login = request.login, password = request.password))
            call.respond(RegisterResponse(accessToken = tokenPair.accessToken, refreshToken = tokenPair.refreshToken))
        }
    }
}