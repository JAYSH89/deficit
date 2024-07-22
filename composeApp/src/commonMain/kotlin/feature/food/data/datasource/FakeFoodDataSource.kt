package feature.food.data.datasource

import core.model.food.AmountType
import core.model.food.Food
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeFoodDataSource : FoodDatasource {
    private val foodState = MutableStateFlow(
        listOf(
            Food(
                id = 1,
                name = "Egg",
                carbs = 0.0,
                proteins = 6.2,
                fats = 4.4,
                calories = 64.0,
                amount = 1.0,
                amountType = AmountType.PIECE,
            ),
        )
    )

    private val foods = mutableListOf(
        Food(
            id = 1,
            name = "Egg",
            carbs = 0.0,
            proteins = 6.2,
            fats = 4.4,
            calories = 64.0,
            amount = 1.0,
            amountType = AmountType.PIECE,
        ),
    )

    override fun getAllFood(): Flow<List<Food>> = foodState.asSharedFlow()

    override fun getFoodById(id: Long): Flow<Food?> = foodState
        .asSharedFlow()
        .map { foods.firstOrNull { it.id == id } }

    override suspend fun insertFood(food: Food) {
        val max = foods.maxOfOrNull { it.id ?: 0 } ?: 0
        val newFood = food.copy(id = max + 1)
        foods.add(newFood)
        foodState.update { foods.toList() }
    }

    override suspend fun updateFood(food: Food) {
        val savedFood = foods.firstOrNull { it.id == food.id }
        foods.remove(savedFood)
        foods.add(food)
        foodState.update { foods.toList() }
    }

    override suspend fun deleteFoodById(id: Long) {
        val savedFood = foods.firstOrNull { it.id == id }
        if (savedFood != null) {
            foods.remove(savedFood)
            foodState.update { foods.toList() }
        }
    }

    override suspend fun deleteAllFood() {
        foods.clear()
        foodState.update { foods.toList() }
    }

    // For testing
    fun addFood(food: List<Food>) {
        foodState.update { food.toList() }
    }
}