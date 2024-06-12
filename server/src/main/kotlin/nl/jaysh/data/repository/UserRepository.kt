package nl.jaysh.data.repository

import nl.jaysh.data.db.UserTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    init {
        transaction { SchemaUtils.create(UserTable) }
    }
}
