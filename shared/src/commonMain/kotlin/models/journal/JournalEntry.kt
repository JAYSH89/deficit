package models.journal

import kotlinx.datetime.LocalDateTime
import models.food.Food

data class JournalEntry(
    val id: String? = null,
    val date: LocalDateTime,
    val amount: Double,
    val food: Food,
)
