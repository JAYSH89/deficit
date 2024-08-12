package core.data.local.db.datasource.weight

import core.model.weight.Weight
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface WeightDataSource {
    fun getAllWeight(): Flow<List<Weight>>
    fun getWeightByDateRange(start: LocalDateTime, end: LocalDateTime): Flow<List<Weight>>
    fun getWeightById(id: Long): Flow<Weight?>
    fun getLatestWeight(): Flow<Weight?>
    suspend fun insertWeight(weight: Weight)
    suspend fun updateWeight(weight: Weight)
    suspend fun deleteWeightById(id: Long)
    suspend fun deleteAllWeight()
}