package nl.jaysh.services

import data.network.models.FoodRequest
import data.network.models.FoodResponse
import nl.jaysh.data.repository.FoodRepository
import java.util.UUID

class FoodService(private val foodRepository: FoodRepository) {

    fun getAllFood(userId: UUID): List<FoodResponse> = foodRepository
        .getAll(userId = userId)
        .map { FoodResponse.fromFood(it) }

    fun findById(foodId: UUID, userId: UUID): FoodResponse? = foodRepository
        .findById(foodId = foodId, userId = userId)
        ?.let { FoodResponse.fromFood(it) }

    fun save(food: FoodRequest, userId: UUID): FoodResponse {
        val createdFood = foodRepository.insert(food = food.toFood(), userId = userId)
        return FoodResponse.fromFood(food = createdFood)
    }

    fun updateFood(food: FoodRequest, userId: UUID): FoodResponse {
        val updatedFood = foodRepository.update(food = food.toFood(), userId = userId)
        return FoodResponse.fromFood(food = updatedFood)
    }

    fun deleteFood(foodId: UUID, userId: UUID) {
        foodRepository.delete(foodId = foodId, userId = userId)
    }
}