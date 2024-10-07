package core.data.repository

import core.data.local.db.datasource.weight.WeightDataSource
import core.model.weight.Weight
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

class ProfileRepositoryImpl(
    private val weightDataSource: WeightDataSource,
) : ProfileRepository {

    override fun getAllWeight(): Flow<List<Weight>> {
        return weightDataSource.getAllWeight()
    }

    override fun getWeightByDateRange(
        start: LocalDateTime,
        end: LocalDateTime,
    ): Flow<List<Weight>> {
        return weightDataSource.getWeightByDateRange(start = start, end = end)
    }

    override fun getWeightById(id: Long): Flow<Weight?> {
        return weightDataSource.getWeightById(id = id)
    }

    override fun getLatestWeight(): Flow<Weight?> {
        return weightDataSource.getLatestWeight()
    }

    override suspend fun insertWeight(weight: Weight) {
        weightDataSource.insertWeight(weight = weight)
    }

    override suspend fun updateWeight(weight: Weight) {
        weightDataSource.updateWeight(weight = weight)
    }

    override suspend fun deleteWeight(id: Long) {
        weightDataSource.deleteWeightById(id = id)
    }

    override suspend fun deleteAllWeight() {
        weightDataSource.deleteAllWeight()
    }
}