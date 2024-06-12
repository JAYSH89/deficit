package nl.jaysh.services

import models.food.Food
import nl.jaysh.data.repository.FoodRepository
import java.util.UUID

class FoodService(private val foodRepository: FoodRepository) {

    fun getAllFood(userId: UUID): List<Food> {
        return foodRepository.getAll(userId = userId)
    }

    fun findById(foodId: UUID, userId: UUID): Food? {
        return foodRepository.findById(foodId = foodId, userId = userId)
    }

    fun save(food: Food, userId: UUID): Food {
        return foodRepository.insert(food = food, userId = userId)
    }

    fun updateFood(food: Food, userId: UUID): Food {
        return foodRepository.update(food = food, userId = userId)
    }

    fun deleteFood(foodId: UUID, userId: UUID) {
        foodRepository.delete(foodId = foodId, userId = userId)
    }
}