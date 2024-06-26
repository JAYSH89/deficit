package nl.jaysh.data.db

import models.user.User
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
import java.util.UUID

object UserTable : UUIDTable(name = "_user") {
    val email: Column<String> = varchar(name = "email", length = 100).uniqueIndex()
    val password: Column<String> = varchar(name = "password", length = 60)
    val createdAt: Column<LocalDateTime?> = datetime(name = "created_at").nullable()
    val updatedAt: Column<LocalDateTime?> = datetime(name = "updated_at").nullable()

    init {
        check(name = "email_not_empty") { email neq "" }
        check(name = "password_not_empty") { password neq "" }
    }
}

fun UserTable.getAll(): List<User> = selectAll().map(ResultRow::toUser)

fun UserTable.findById(userId: UUID): User? = selectAll()
    .where { UserTable.id eq userId }
    .map(ResultRow::toUser)
    .singleOrNull()

fun UserTable.findByEmail(email: String): User? = selectAll()
    .where { UserTable.email eq email }
    .map(ResultRow::toUser)
    .singleOrNull()

fun UserTable.insert(email: String, password: String): User {
    val id = insertAndGetId {
        it[UserTable.email] = email
        it[UserTable.password] = password
        it[createdAt] = LocalDateTime.now()
        it[updatedAt] = LocalDateTime.now()
    }.value

    val newUser = findById(id)
    requireNotNull(newUser)

    return newUser
}

fun UserTable.update(user: User): User {
    val userId = UUID.fromString(user.id)

    val rowsChanged = update({ id eq userId }) {
        it[email] = user.email
        it[password] = user.password
        it[updatedAt] = LocalDateTime.now()
    }
    check(rowsChanged == 1)

    val updatedUser = findById(userId)
    requireNotNull(updatedUser)

    return updatedUser
}

fun UserTable.delete(userId: UUID) {
    val rowsChanged = deleteWhere { UserTable.id eq userId }
    check(rowsChanged == 1)
}

fun ResultRow.toUser() = User(
    id = this[UserTable.id].value.toString(),
    email = this[UserTable.email],
    password = this[UserTable.password],
)
