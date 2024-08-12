package core.data.repository

import core.model.weight.Weight
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface ProfileRepository {
    fun getAllWeight(): Flow<List<Weight>>
    fun getWeightByDateRange(start: LocalDateTime, end: LocalDateTime): Flow<List<Weight>>
    fun getWeightById(id: Long): Flow<Weight?>
    fun getLatestWeight(): Flow<Weight?>
    suspend fun insertWeight(weight: Weight)
    suspend fun updateWeight(weight: Weight)
    suspend fun deleteWeight(id: Long)
    suspend fun deleteAllWeight()
}
