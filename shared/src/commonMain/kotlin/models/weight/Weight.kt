package models.weight

import kotlinx.datetime.LocalDateTime

data class Weight(
    val id: String? = null,
    val weight: Double,
    val measuredAt: LocalDateTime,
)
