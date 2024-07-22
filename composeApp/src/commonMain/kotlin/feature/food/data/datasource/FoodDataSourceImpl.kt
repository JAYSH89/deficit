package feature.food.data.datasource

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import core.data.local.db.entity.toFood
import core.model.food.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.jaysh.database.Database

class FoodDataSourceImpl(private val database: Database) : FoodDatasource {
    private val queries = database.foodQueries

    override fun getAllFood(): Flow<List<Food>> = queries.getAllFoods()
        .asFlow()
        .mapToList(Dispatchers.IO)
        .map { list ->
            list.map { entity -> entity.toFood() }
        }


    override fun getFoodById(id: Long): Flow<Food?> = queries.getFoodById(id = id)
        .asFlow()
        .mapToOne(Dispatchers.IO)
        .map { it.toFood() }

    override suspend fun insertFood(food: Food) {
        queries.insertFood(
            name = food.name,
            carbs = food.carbs,
            proteins = food.proteins,
            fats = food.fats,
            calories = food.calories,
            amount = food.amount,
            amount_type = food.amountType.toString(),
        )
    }

    override suspend fun updateFood(food: Food) {
        requireNotNull(food.id)

        queries.updateFood(
            id = food.id,
            name = food.name,
            carbs = food.carbs,
            proteins = food.proteins,
            fats = food.fats,
            calories = food.calories,
            amount = food.amount,
            amount_type = food.amountType.toString(),
        )
    }

    override suspend fun deleteFoodById(id: Long) {
        queries.deleteFoodById(id = id)
    }

    override suspend fun deleteAllFood() {
        queries.deleteAllFoods()
    }
}