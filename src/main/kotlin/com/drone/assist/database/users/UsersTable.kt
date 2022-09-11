package com.drone.assist.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UsersTable : Table("users") {
    private val login = UsersTable.varchar("login", 25)
    private val password = UsersTable.varchar("password", 25)

    fun create(user: UserDto) {
        transaction {
            UsersTable.insert {
                it[login] = user.login
                it[password] = user.password
            }
        }
    }

    fun get(login: String): UserDto? {
        return transaction {
            try {
                val user = UsersTable.select {
                    UsersTable.login.eq(login)
                }.single()

                return@transaction UserDto(
                    login = user[UsersTable.login],
                    password = user[password]
                )
            } catch (e: Exception) {
                return@transaction null
            }

        }
    }
}