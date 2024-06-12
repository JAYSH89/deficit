package models.journal

data class JournalSummary(
    val journal: List<JournalEntry>,
    val totalCarbs: Double,
    val totalProteins: Double,
    val totalFats: Double,
    val caloriesFromCarbs: Double,
    val caloriesFromProteins: Double,
    val caloriesFromFats: Double,
    val totalCalories: Double,
)
