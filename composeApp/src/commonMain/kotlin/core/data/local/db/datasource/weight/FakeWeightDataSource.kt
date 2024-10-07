package core.data.local.db.datasource.weight

import core.model.weight.Weight
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

class FakeWeightDataSource : WeightDataSource {
    override fun getAllWeight(): Flow<List<Weight>> {
        TODO("Not yet implemented")
    }

    override fun getWeightByDateRange(
        start: LocalDateTime,
        end: LocalDateTime,
    ): Flow<List<Weight>> {
        TODO("Not yet implemented")
    }

    override fun getWeightById(id: Long): Flow<Weight?> {
        TODO("Not yet implemented")
    }

    override fun getLatestWeight(): Flow<Weight?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertWeight(weight: Weight) {
        TODO("Not yet implemented")
    }

    override suspend fun updateWeight(weight: Weight) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWeightById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllWeight() {
        TODO("Not yet implemented")
    }
}