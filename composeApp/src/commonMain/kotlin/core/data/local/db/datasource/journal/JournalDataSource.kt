package core.data.local.db.datasource.journal

import core.model.journal.JournalEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface JournalDataSource {
    fun getAllJournalEntries(): Flow<List<JournalEntry>>
    fun getByDateRange(start: LocalDateTime, end: LocalDateTime): Flow<List<JournalEntry>>
    fun getJournalById(id: Long): Flow<JournalEntry?>
    suspend fun insertJournalEntry(journalEntry: JournalEntry)
    suspend fun updateJournalEntry(journalEntry: JournalEntry)
    suspend fun deleteJournalEntry(id: Long)
    suspend fun deleteAllJournalEntries()
}