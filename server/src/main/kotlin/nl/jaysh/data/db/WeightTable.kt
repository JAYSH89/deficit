package nl.jaysh.data.db

import kotlinx.datetime.toKotlinLocalDateTime
import models.weight.Weight
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object WeightTable : LongIdTable(name = "weight") {
    val weight: Column<Double> = double(name = "weight")
    val measuredAt: Column<LocalDateTime> = datetime(name = "measured_at")
    val createdAt: Column<LocalDateTime?> = datetime(name = "created_at").nullable()
    val updatedAt: Column<LocalDateTime?> = datetime(name = "updated_at").nullable()

    val user: Column<EntityID<Long>> = reference(
        name = "user_id",
        refColumn = UserTable.id,
        onDelete = ReferenceOption.CASCADE,
    )
}

fun ResultRow.toWeight(): Weight = Weight(
    id = this[WeightTable.id].value,
    weight = this[WeightTable.weight],
    measuredAt = this[WeightTable.measuredAt].toKotlinLocalDateTime(),
)
