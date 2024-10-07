package core.data.repository

import core.data.local.db.datasource.journal.JournalDataSource
import core.model.journal.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

class JournalRepositoryImpl(private val journalDataSource: JournalDataSource) : JournalRepository {

    override fun getAllJournalEntries(): Flow<List<JournalEntry>> {
        return journalDataSource.getAllJournalEntries()
    }

    override fun getByDateRange(
        start: LocalDateTime,
        end: LocalDateTime,
    ): Flow<List<JournalEntry>> {
        return journalDataSource.getByDateRange(start = start, end = end)
    }

    override fun getJournalById(id: Long): Flow<JournalEntry?> {
        return journalDataSource.getJournalById(id = id)
    }

    override suspend fun insertJournalEntry(journalEntry: JournalEntry) {
        journalDataSource.insertJournalEntry(journalEntry = journalEntry)
    }

    override suspend fun updateJournalEntry(journalEntry: JournalEntry) {
        journalDataSource.updateJournalEntry(journalEntry = journalEntry)
    }

    override suspend fun deleteJournalEntry(id: Long) {
        journalDataSource.deleteJournalEntry(id = id)
    }

    override suspend fun deleteAllJournalEntries() {
        journalDataSource.deleteAllJournalEntries()
    }
}