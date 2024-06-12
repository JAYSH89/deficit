package models.journal

import kotlinx.datetime.LocalDateTime
import models.food.Food

data class JournalEntry(
    val id: Long,
    val date: LocalDateTime,
    val amount: Double,
    val food: Food,
)
