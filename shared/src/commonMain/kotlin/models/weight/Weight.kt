package models.weight

import kotlinx.datetime.LocalDateTime

data class Weight(
    val id: String,
    val weight: Double,
    val measuredAt: LocalDateTime,
)
