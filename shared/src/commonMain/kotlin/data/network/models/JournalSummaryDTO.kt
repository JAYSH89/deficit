package data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.journal.JournalSummary

@Serializable
data class JournalSummaryResponse(
    @SerialName("journal")
    val journal: List<JournalEntryResponse>,

    @SerialName("total_carbs")
    val totalCarbs: Double,

    @SerialName("total_proteins")
    val totalProteins: Double,

    @SerialName("total_fats")
    val totalFats: Double,

    @SerialName("calories_from_carbs")
    val caloriesFromCarbs: Double,

    @SerialName("calories_from_proteins")
    val caloriesFromProteins: Double,

    @SerialName("calories_from_fats")
    val caloriesFromFats: Double,

    @SerialName("total_calories")
    val totalCalories: Double,
) {
    companion object {
        fun fromJournalSummary(journalSummary: JournalSummary): JournalSummaryResponse {
            return JournalSummaryResponse(
                journal = journalSummary.journal.map { JournalEntryResponse.fromJournalEntry(it) },
                totalCarbs = journalSummary.totalCarbs,
                totalProteins = journalSummary.totalProteins,
                totalFats = journalSummary.totalFats,
                caloriesFromCarbs = journalSummary.caloriesFromCarbs,
                caloriesFromProteins = journalSummary.caloriesFromProteins,
                caloriesFromFats = journalSummary.caloriesFromFats,
                totalCalories = journalSummary.totalCalories,
            )
        }
    }
}