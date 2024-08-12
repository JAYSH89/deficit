package core.data.local.db.datasource.journal

import core.model.journal.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

class FakeJournalDataSource : JournalDataSource {

    override fun getAllJournalEntries(): Flow<List<JournalEntry>> {
        TODO("Not yet implemented")
    }

    override fun getByDateRange(
        start: LocalDateTime,
        end: LocalDateTime,
    ): Flow<List<JournalEntry>> {
        TODO("Not yet implemented")
    }

    override fun getJournalById(id: Long): Flow<JournalEntry?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertJournalEntry(journalEntry: JournalEntry) {
        TODO("Not yet implemented")
    }

    override suspend fun updateJournalEntry(journalEntry: JournalEntry) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteJournalEntry(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllJournalEntries() {
        TODO("Not yet implemented")
    }
}