package models.weight

import kotlinx.datetime.LocalDateTime

data class Weight(
    val id: Long,
    val weight: Double,
    val measuredAt: LocalDateTime,
)
