package nl.jaysh.data.repository

import models.user.User
import nl.jaysh.data.db.UserTable
import nl.jaysh.data.db.delete
import nl.jaysh.data.db.findByEmail
import nl.jaysh.data.db.findById
import nl.jaysh.data.db.getAll
import nl.jaysh.data.db.insert
import nl.jaysh.data.db.update
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserRepository {

    init {
        transaction { SchemaUtils.create(UserTable) }
    }

    fun getAll(): List<User> = transaction {
        UserTable.getAll()
    }

    fun findById(userId: UUID): User? = transaction {
        UserTable.findById(userId = userId)
    }

    fun findByEmail(email: String): User? = transaction {
        UserTable.findByEmail(email = email)
    }

    fun insert(email: String, password: String): User = transaction {
        UserTable.insert(email = email, password = password)
    }

    fun update(user: User): User = transaction {
        UserTable.update(user = user)
    }

    fun delete(userId: UUID) = transaction {
        UserTable.delete(userId = userId)
    }
}
