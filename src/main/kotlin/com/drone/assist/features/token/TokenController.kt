package com.drone.assist.features.token

import com.drone.assist.database.tokens.TokenTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class TokenController {
    suspend fun refreshToken(call: ApplicationCall) {
        val request = call.receive<RefreshTokenRequest>()
        val tokenDto = TokenTable.get(refreshToken = request.refreshToken)

        if (tokenDto == null) {
            call.respond(status = HttpStatusCode.BadRequest, message = "invalid token")
        } else {
            val tokenPair = generateTokenPair(tokenDto.login)
            call.respond(
                RefreshTokenResponse(
                    accessToken = tokenPair.accessToken,
                    refreshToken = tokenPair.refreshToken
                )
            )
        }
    }
}