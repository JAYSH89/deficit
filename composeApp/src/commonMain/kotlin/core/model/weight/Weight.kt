package core.model.weight

import kotlinx.datetime.LocalDateTime

data class Weight(
    val id: Long? = null,
    val weight: Double,
    val date: LocalDateTime,
)
