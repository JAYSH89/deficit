package nl.jaysh.data.repository

import nl.jaysh.data.db.TokenTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class TokenRepository {

    init {
        transaction { SchemaUtils.create(TokenTable) }
    }
}
