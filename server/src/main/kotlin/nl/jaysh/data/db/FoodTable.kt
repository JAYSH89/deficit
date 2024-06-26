package nl.jaysh.data.db

import models.AmountType
import models.food.Food
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
import java.util.UUID

object FoodTable : UUIDTable(name = "food") {
    val name: Column<String> = varchar(name = "name", length = 100)
    val carbs: Column<Double> = double(name = "carbs")
    val proteins: Column<Double> = double(name = "proteins")
    val fats: Column<Double> = double(name = "fats")
    val amount: Column<Double> = double(name = "amount")
    val amountType: Column<String> = varchar(name = "amount_type", length = 50)
    val createdAt: Column<LocalDateTime?> = datetime(name = "created_at").nullable()
    val updatedAt: Column<LocalDateTime?> = datetime(name = "updated_at").nullable()

    val user: Column<EntityID<UUID>> = reference(
        name = "user_id",
        refColumn = UserTable.id,
        onDelete = ReferenceOption.CASCADE,
    )
}

fun FoodTable.getAll(userId: UUID): List<Food> = selectAll()
    .where { user eq userId }
    .map(ResultRow::toFood)

fun FoodTable.findById(foodId: UUID, userId: UUID): Food? = selectAll()
    .where { (id eq foodId) and (user eq userId) }
    .map(ResultRow::toFood)
    .singleOrNull()

fun FoodTable.insert(food: Food, userId: UUID): Food {
    val id = insertAndGetId {
        it[name] = food.name
        it[carbs] = food.carbs
        it[proteins] = food.proteins
        it[fats] = food.fats
        it[amount] = food.amount
        it[amountType] = food.amountType.toString()
        it[createdAt] = LocalDateTime.now()
        it[updatedAt] = LocalDateTime.now()
        it[user] = userId
    }.value

    val newFood = findById(foodId = id, userId = userId)
    requireNotNull(newFood)

    return newFood
}

fun FoodTable.update(food: Food, userId: UUID): Food {
    requireNotNull(food.id)

    val foodId = UUID.fromString(food.id)

    val rowsChanged = update({ (FoodTable.id eq foodId) and (user eq userId) }) {
        it[name] = food.name
        it[carbs] = food.carbs
        it[proteins] = food.proteins
        it[fats] = food.fats
        it[amount] = food.amount
        it[amountType] = food.amountType.toString()
        it[updatedAt] = LocalDateTime.now()
    }
    check(rowsChanged == 1)

    val updatedFood = findById(foodId = foodId, userId = userId)
    requireNotNull(updatedFood)

    return updatedFood
}

fun FoodTable.delete(foodId: UUID, userId: UUID) {
    val rowsChanged = deleteWhere { (id eq foodId) and (user eq userId) }
    check(rowsChanged == 1)
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
