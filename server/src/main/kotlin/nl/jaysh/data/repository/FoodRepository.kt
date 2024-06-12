package nl.jaysh.data.repository

import nl.jaysh.data.db.FoodTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class FoodRepository {

    init {
        transaction { SchemaUtils.create(FoodTable) }
    }
}