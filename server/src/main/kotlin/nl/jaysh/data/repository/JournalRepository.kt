package nl.jaysh.data.repository

import nl.jaysh.data.db.JournalTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class JournalRepository {

    init {
        transaction { SchemaUtils.create(JournalTable) }
    }
}