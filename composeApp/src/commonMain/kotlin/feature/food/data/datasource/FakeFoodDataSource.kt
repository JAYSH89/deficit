package feature.food.data.datasource

import core.model.food.AmountType
import core.model.food.Food
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeFoodDataSource : FoodDatasource {
    private val foodState = MutableStateFlow(
        mutableListOf(
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

    override fun getAllFood(): Flow<List<Food>> = foodState.asStateFlow()

    override fun getFoodById(id: Long): Flow<Food?> = foodState
        .asStateFlow()
        .map {
            foods.firstOrNull { it.id == id }
        }

    override suspend fun insertFood(food: Food) {
        val max = foods.maxOfOrNull { it.id ?: 0 } ?: 0
        val newFood = food.copy(id = max + 1)
        foods.add(newFood)
        foodState.update { foods }
    }

    override suspend fun updateFood(food: Food) {
        val savedFood = foods.firstOrNull { it.id == food.id }
        foods.remove(savedFood)
        foods.add(food)
        foodState.update { foods }
    }

    override suspend fun deleteFoodById(id: Long) {
        val savedFood = foods.firstOrNull { it.id == id }
        Napier.d("DELETE FOOD $foods")
        Napier.d("DELETE FOOD BY ID ($id)")
        Napier.d("$savedFood")
        if (savedFood != null) {
            foods.remove(savedFood)
            Napier.d("$foods")
            foodState.update { foods }
        }
    }

    override suspend fun deleteAllFood() {
        foods.clear()
        foodState.update { foods }
    }
}