package nl.jaysh.services

import data.network.models.WeightRequest
import data.network.models.WeightResponse
import nl.jaysh.data.repository.WeightRepository
import java.util.UUID

class WeightService(private val weightRepository: WeightRepository) {

    fun getAll(userId: UUID): List<WeightResponse> = weightRepository
        .getAll(userId = userId)
        .map { weight -> WeightResponse.fromWeight(weight) }

    fun insert(weight: WeightRequest, userId: UUID): WeightResponse {
        val savedWeight = weightRepository.insert(
            weightEntry = weight.toWeight(),
            userId = userId,
        )

        return WeightResponse.fromWeight(weight = savedWeight)
    }

    fun update(weight: WeightRequest, userId: UUID): WeightResponse {
        val updatedWeight = weightRepository.update(
            weightEntry = weight.toWeight(),
            userId = userId,
        )

        return WeightResponse.fromWeight(weight = updatedWeight)
    }

    fun delete(weightId: UUID, userId: UUID) {
        weightRepository.delete(weightId = weightId, userId = userId)
    }
}