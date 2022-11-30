package com.drone.assist.database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object TokenTable: Table("tokens") {

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
}