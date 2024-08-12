package core.data.local.db.entity

import core.model.food.AmountType
import core.model.food.Food
import core.model.journal.JournalEntry
import kotlinx.datetime.LocalDateTime
import nl.jaysh.database.GetAllJournals
import nl.jaysh.database.GetJournalByDateRange
import nl.jaysh.database.GetJournalById

// TODO: Derpy code - fix
fun GetJournalById.toJournalEntry() = JournalEntry(
    id = this.journal_id,
    food = this.toFood(),
    serving = this.journal_amount,
    date = LocalDateTime.parse(this.journal_date),
)

fun GetJournalById.toFood() = Food(
    id = this.food_id,
    name = this.food_name,
    carbs = this.food_carbs,
    proteins = this.food_proteins,
    fats = this.food_fats,
    calories = this.food_calories,
    amount = this.food_amount,
    amountType = AmountType.valueOf(this.food_amount_type),
)

fun GetAllJournals.toJournalEntry() = JournalEntry(
    id = this.journal_id,
    food = this.toFood(),
    serving = this.journal_amount,
    date = LocalDateTime.parse(this.journal_date),
)

fun GetAllJournals.toFood() = Food(
    id = this.food_id,
    name = this.food_name,
    carbs = this.food_carbs,
    proteins = this.food_proteins,
    fats = this.food_fats,
    calories = this.food_calories,
    amount = this.food_amount,
    amountType = AmountType.valueOf(this.food_amount_type),
)

fun GetJournalByDateRange.toJournalEntry() = JournalEntry(
    id = this.journal_id,
    food = this.toFood(),
    serving = this.journal_amount,
    date = LocalDateTime.parse(this.journal_date),
)

fun GetJournalByDateRange.toFood() = Food(
    id = this.food_id,
    name = this.food_name,
    carbs = this.food_carbs,
    proteins = this.food_proteins,
    fats = this.food_fats,
    calories = this.food_calories,
    amount = this.food_amount,
    amountType = AmountType.valueOf(this.food_amount_type),
)