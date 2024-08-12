package core.data.repository

import core.model.food.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getAllFood(): Flow<List<Food>>
    fun getFoodById(id: Long): Flow<Food?>
    suspend fun insertFood(food: Food)
    suspend fun updateFood(food: Food)
    suspend fun deleteFoodById(id: Long)
    suspend fun deleteAllFood()
}