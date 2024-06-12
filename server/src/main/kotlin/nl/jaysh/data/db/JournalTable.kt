package nl.jaysh.data.db

import kotlinx.datetime.toKotlinLocalDateTime
import models.journal.JournalEntry
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

object JournalTable : UUIDTable(name = "journal") {
    val date: Column<LocalDateTime> = datetime(name = "date")
    val amount: Column<Double> = double(name = "amount")
    val createdAt: Column<LocalDateTime?> = datetime(name = "created_at").nullable()
    val updatedAt: Column<LocalDateTime?> = datetime(name = "updated_at").nullable()

    val food: Column<EntityID<UUID>> = reference(
        name = "food",
        refColumn = FoodTable.id,
        onDelete = ReferenceOption.CASCADE,
    )

    val user: Column<EntityID<UUID>> = reference(
        name = "user_id",
        refColumn = UserTable.id,
        onDelete = ReferenceOption.CASCADE,
    )
}

fun ResultRow.toJournalEntry(): JournalEntry = JournalEntry(
    id = this[JournalTable.id].value.toString(),
    date = this[JournalTable.date].toKotlinLocalDateTime(),
    amount = this[JournalTable.amount],
    food = this.toFood(),
)
