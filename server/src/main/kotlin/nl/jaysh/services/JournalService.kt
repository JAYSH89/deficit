package nl.jaysh.services

import data.network.models.JournalEntryRequest
import models.journal.JournalEntry
import models.journal.JournalSummary
import nl.jaysh.data.repository.FoodRepository
import nl.jaysh.data.repository.JournalRepository
import java.time.LocalDateTime
import java.util.UUID

class JournalService(
    private val journalRepository: JournalRepository,
    private val foodRepository: FoodRepository,
) {
    fun getAllJournalEntries(userId: UUID): List<JournalEntry> {
        return journalRepository.getAll(userId = userId)
    }

    fun findById(journalEntryId: UUID, userId: UUID): JournalEntry? {
        return journalRepository.findById(journalEntryId = journalEntryId, userId = userId)
    }

    fun getBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        userId: UUID,
    ): List<JournalEntry> = journalRepository.getBetween(
        startDate = startDate,
        endDate = endDate,
        userId = userId,
    )

    fun save(request: JournalEntryRequest, userId: UUID): JournalEntry {
        val foodId = UUID.fromString(request.food)
        val food = foodRepository.findById(foodId = foodId, userId = userId)
        requireNotNull(food)

        val journalEntry = JournalEntry(
            date = request.date,
            amount = request.amount,
            food = food,
        )

        return journalRepository.insert(journalEntry = journalEntry, userId = userId)
    }

    fun delete(journalEntryId: UUID, userId: UUID) {
        journalRepository.delete(journalEntryId = journalEntryId, userId = userId)
    }

    fun getSummaryBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        userId: UUID,
    ): JournalSummary {
        val journal = journalRepository.getBetween(
            startDate = startDate,
            endDate = endDate,
            userId = userId,
        )

        val totalCarbs = journal.sumOf { it.food.carbs }
        val totalProteins = journal.sumOf { it.food.proteins }
        val totalFats = journal.sumOf { it.food.fats }

        val caloriesFromCarbs = totalCarbs * 4
        val caloriesFromProteins = totalProteins * 4
        val caloriesFromFats = totalFats * 9

        return JournalSummary(
            journal = journal,
            totalCarbs = totalCarbs,
            totalProteins = totalProteins,
            totalFats = totalFats,
            caloriesFromCarbs = caloriesFromCarbs,
            caloriesFromProteins = caloriesFromProteins,
            caloriesFromFats = caloriesFromFats,
            totalCalories = caloriesFromCarbs + caloriesFromProteins + caloriesFromFats,
        )
    }
}
