package com.drone.assist.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object TokenTable : Table("tokens") {

    private val id = TokenTable.varchar("id", 50)
    private val login = TokenTable.varchar("login", 25)
    private val token = TokenTable.varchar("token", 300)

    fun create(tokenDto: TokenDto) {
        transaction {
            TokenTable.insert {
                it[id] = tokenDto.id
                it[login] = tokenDto.login
                it[token] = tokenDto.token
            }
        }
    }

    fun removeByLogin(userLogin: String) {
        transaction {
            TokenTable.deleteWhere {
                login eq userLogin
            }
        }
    }

    fun get(refreshToken: String): TokenDto? {
        return transaction {
            TokenTable.select {
                token eq refreshToken
            }.map {
                TokenDto(
                    id = it[TokenTable.id],
                    login = it[login],
                    token = it[token]
                )
            }.firstOrNull()
        }
    }
}