package nl.jaysh.data.db

import models.user.User
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object UserTable : LongIdTable(name = "_user") {
    val email: Column<String> = varchar(name = "email", length = 100).uniqueIndex()
    val password: Column<String> = varchar(name = "password", length = 60)
    val createdAt: Column<LocalDateTime?> = datetime(name = "created_at").nullable()
    val updatedAt: Column<LocalDateTime?> = datetime(name = "updated_at").nullable()

    init {
        check(name = "email_not_empty") { email neq "" }
        check(name = "password_not_empty") { password neq "" }
    }
}

fun ResultRow.toUser() = User(
    id = this[UserTable.id].value,
    email = this[UserTable.email],
    password = this[UserTable.password],
)
