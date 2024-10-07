package core.data.repository

import core.model.food.Food
import core.data.local.db.datasource.food.FoodDatasource
import kotlinx.coroutines.flow.Flow

class FoodRepositoryImpl(private val datasource: FoodDatasource) : FoodRepository {

    override fun getAllFood(): Flow<List<Food>> = datasource.getAllFood()

    override fun getFoodById(id: Long): Flow<Food?> = datasource.getFoodById(id = id)

    override suspend fun insertFood(food: Food) = datasource.insertFood(food = food)

    override suspend fun updateFood(food: Food) = datasource.updateFood(food = food)

    override suspend fun deleteFoodById(id: Long) {
        datasource.deleteFoodById(id = id)
    }

    override suspend fun deleteAllFood() {
        datasource.deleteAllFood()
    }
}