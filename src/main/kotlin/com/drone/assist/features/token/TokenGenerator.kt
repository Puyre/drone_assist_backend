package com.drone.assist.features.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.drone.assist.database.tokens.TokenDto
import com.drone.assist.database.tokens.TokenTable
import java.time.Duration
import java.util.*

const val ACCESS_TOKEN_LIFETIME_IN_MINUTES = 120L

fun generateTokenPair(userLogin: String): TokenPair {
    val currentTime = System.currentTimeMillis()

    val accessToken =
        JWT.create()
            .withAudience(JWTCredentialsProvider.audience)
            .withIssuer(JWTCredentialsProvider.issuer)
            .withClaim("username", userLogin)
            .withExpiresAt(Date(currentTime + Duration.ofMinutes(ACCESS_TOKEN_LIFETIME_IN_MINUTES).toMillis()))
            .sign(Algorithm.HMAC256(JWTCredentialsProvider.secret))

    val refreshToken = UUID.randomUUID().toString()

    TokenTable.removeByLogin(userLogin)
    TokenTable.create(TokenDto(id = UUID.randomUUID().toString(), token = refreshToken, login = userLogin))

    return TokenPair(accessToken = accessToken, refreshToken = refreshToken)
}