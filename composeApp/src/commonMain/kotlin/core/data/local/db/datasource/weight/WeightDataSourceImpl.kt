package core.data.local.db.datasource.weight

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import core.data.local.db.entity.toWeight
import core.di.DefaultDispatcherProvider
import core.model.weight.Weight
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import nl.jaysh.database.Database

class WeightDataSourceImpl(
    private val database: Database,
    private val dispatcherProvider: DefaultDispatcherProvider,
) : WeightDataSource {
    private val queries = database.weightQueries

    override fun getAllWeight(): Flow<List<Weight>> = queries
        .getAllWeights()
        .asFlow()
        .mapToList(dispatcherProvider.io)
        .map { list ->
            list.map { entity -> entity.toWeight() }
        }

    override fun getWeightByDateRange(
        start: LocalDateTime,
        end: LocalDateTime,
    ): Flow<List<Weight>> = queries
        .getWeightByDateRange(start.toString(), end.toString())
        .asFlow()
        .mapToList(dispatcherProvider.io)
        .map { list ->
            list.map { entity -> entity.toWeight() }
        }

    override fun getWeightById(id: Long): Flow<Weight?> {
        return queries.getWeightById(id = id)
            .asFlow()
            .mapToOneOrNull(dispatcherProvider.io)
            .map { entity -> entity?.toWeight() }
    }

    override fun getLatestWeight(): Flow<Weight?> = queries
        .getLatestWeight()
        .asFlow()
        .mapToOneOrNull(dispatcherProvider.io)
        .map { entity -> entity?.toWeight() }

    override suspend fun insertWeight(weight: Weight) {
        queries.insertWeight(
            weight = weight.weight,
            date = weight.date.toString(),
        )
    }

    override suspend fun updateWeight(weight: Weight) {
        requireNotNull(weight.id)

        queries.updateWeight(
            id = weight.id,
            weight = weight.weight,
            date = weight.date.toString(),
        )
    }

    override suspend fun deleteWeightById(id: Long) {
        queries.deleteWeightById(id = id)
    }

    override suspend fun deleteAllWeight() {
        queries.deleteAllWeight()
    }
}