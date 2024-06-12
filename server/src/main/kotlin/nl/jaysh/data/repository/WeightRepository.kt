package nl.jaysh.data.repository

import nl.jaysh.data.db.WeightTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class WeightRepository {

    init {
        transaction { SchemaUtils.create(WeightTable) }
    }
}
