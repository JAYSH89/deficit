package data.network.models

import data.serializers.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.journal.JournalEntry

@Serializable
data class JournalEntryRequest(
    @SerialName("date")
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,

    @SerialName("amount")
    val amount: Double,

    @SerialName("food")
    val food: String,
)

@Serializable
data class JournalEntryResponse(
    @SerialName("id")
    val id: String? = null,

    @SerialName("date")
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,

    @SerialName("amount")
    val amount: Double,

    @SerialName("food")
    val food: FoodResponse,
) {
    companion object {
        fun fromJournalEntry(journalEntry: JournalEntry): JournalEntryResponse {
            return JournalEntryResponse(
                id = journalEntry.id,
                date = journalEntry.date,
                amount = journalEntry.amount,
                food = FoodResponse.fromFood(journalEntry.food),
            )
        }
    }
}
