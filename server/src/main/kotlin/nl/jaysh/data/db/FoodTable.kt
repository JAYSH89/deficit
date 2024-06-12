package nl.jaysh.data.db

import models.AmountType
import models.food.Food
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

object FoodTable : UUIDTable(name = "food") {
    val name: Column<String> = varchar(name = "name", length = 100)
    val carbs: Column<Double> = double(name = "carbs")
    val proteins: Column<Double> = double(name = "proteins")
    val fats: Column<Double> = double(name = "fats")
    val amount: Column<Double> = double(name = "amount")
    val amountType: Column<String> = ProfileTable.varchar(name = "amount_type", length = 50)
    val createdAt: Column<LocalDateTime?> = datetime(name = "created_at").nullable()
    val updatedAt: Column<LocalDateTime?> = datetime(name = "updated_at").nullable()

    val user: Column<EntityID<UUID>> = reference(
        name = "user_id",
        refColumn = UserTable.id,
        onDelete = ReferenceOption.CASCADE,
    )
}

fun ResultRow.toFood() = Food(
    id = this[FoodTable.id].value.toString(),
    name = this[FoodTable.name],
    carbs = this[FoodTable.carbs],
    proteins = this[FoodTable.proteins],
    fats = this[FoodTable.fats],
    amount = this[FoodTable.amount],
    amountType = AmountType.fromString(this[FoodTable.amountType]),
)
