package nl.jaysh.data.repository

import models.weight.Weight
import nl.jaysh.data.db.WeightTable
import nl.jaysh.data.db.delete
import nl.jaysh.data.db.findById
import nl.jaysh.data.db.getAll
import nl.jaysh.data.db.insert
import nl.jaysh.data.db.update
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class WeightRepository {

    init {
        transaction { SchemaUtils.create(WeightTable) }
    }

    fun getAll(userId: UUID): List<Weight> = transaction {
        WeightTable.getAll(userId = userId)
    }

    fun findById(weightId: UUID, userId: UUID): Weight? = transaction {
        WeightTable.findById(weightId = weightId, userId = userId)
    }

    fun insert(weightEntry: Weight, userId: UUID): Weight = transaction {
        WeightTable.insert(weightEntry = weightEntry, userId = userId)
    }

    fun update(weightEntry: Weight, userId: UUID): Weight = transaction {
        WeightTable.update(weightEntry = weightEntry, userId = userId)
    }

    fun delete(weightId: UUID, userId: UUID) = transaction {
        WeightTable.delete(weightId = weightId, userId = userId)
    }
}
