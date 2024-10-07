package core.model.journal

import core.model.food.Food
import kotlinx.datetime.LocalDateTime

data class JournalEntry(
    val id: Long? = null,
    val food: Food,
    val serving: Double,
    val date: LocalDateTime,
) {
    val calories: Double
        get() = serving * food.calories

    val carbs: Double
        get() = serving * food.carbs

    val proteins: Double
        get() = serving * food.proteins

    val fats: Double
        get() = serving * food.fats
}
