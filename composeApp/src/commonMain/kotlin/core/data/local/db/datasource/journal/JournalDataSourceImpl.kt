package core.data.local.db.datasource.journal

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import core.data.local.db.entity.toJournalEntry
import core.di.DefaultDispatcherProvider
import core.model.journal.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import nl.jaysh.database.Database

class JournalDataSourceImpl(
    private val database: Database,
    private val dispatcherProvider: DefaultDispatcherProvider,
) : JournalDataSource {
    private val queries = database.journalQueries

    override fun getAllJournalEntries(): Flow<List<JournalEntry>> = queries
        .getAllJournals()
        .asFlow()
        .mapToList(dispatcherProvider.io)
        .map { list ->
            list.map { entity -> entity.toJournalEntry() }
        }

    override fun getByDateRange(
        start: LocalDateTime,
        end: LocalDateTime,
    ): Flow<List<JournalEntry>> = queries
        .getJournalByDateRange(start.toString(), end.toString())
        .asFlow()
        .mapToList(dispatcherProvider.io)
        .map { list ->
            list.map { entity -> entity.toJournalEntry() }
        }

    override fun getJournalById(id: Long): Flow<JournalEntry?> {
        return queries.getJournalById(id = id)
            .asFlow()
            .mapToOneOrNull(dispatcherProvider.io)
            .map { entity -> entity?.toJournalEntry() }
    }

    // TODO: Error handling
    override suspend fun insertJournalEntry(journalEntry: JournalEntry) {
        requireNotNull(journalEntry.food.id)

        queries.insertJournal(
            food_id = journalEntry.food.id,
            amount = journalEntry.serving,
            date = journalEntry.date.toString(),
        )
    }

    // TODO: Error handling
    override suspend fun updateJournalEntry(journalEntry: JournalEntry) {
        requireNotNull(journalEntry.id)
        requireNotNull(journalEntry.food.id)

        queries.updateJournal(
            id = journalEntry.id,
            food_id = journalEntry.food.id,
            amount = journalEntry.serving,
            date = journalEntry.date.toString(),
        )
    }

    override suspend fun deleteJournalEntry(id: Long) {
        queries.deleteJournal(id = id)
    }

    override suspend fun deleteAllJournalEntries() {
        queries.deleteAllJournal()
    }
}
