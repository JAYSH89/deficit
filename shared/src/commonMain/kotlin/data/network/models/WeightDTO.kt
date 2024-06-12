package data.network.models

import data.serializers.LocalDateSerializer
import data.serializers.LocalDateTimeSerializer
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.weight.Weight

@Serializable
data class WeightRequest(
    @SerialName("id")
    val id: String? = null,

    @SerialName("weight")
    val weight: Double,

    @SerialName("measured_at")
    @Serializable(with = LocalDateSerializer::class)
    val measuredAt: LocalDate,
) {
    fun toWeight(): Weight {
        val time = LocalTime(0, 0, 0, 0)
        return Weight(
            id = id,
            weight = weight,
            measuredAt = measuredAt.atTime(time),
        )
    }
}

@Serializable
data class WeightResponse(
    @SerialName("id")
    val id: String? = null,

    @SerialName("weight")
    val weight: Double,

    @SerialName("measured_at")
    @Serializable(with = LocalDateTimeSerializer::class)
    val measuredAt: LocalDateTime,
) {
    companion object {
        fun fromWeight(weight: Weight): WeightResponse = WeightResponse(
            id = weight.id,
            weight = weight.weight,
            measuredAt = weight.measuredAt,
        )
    }
}