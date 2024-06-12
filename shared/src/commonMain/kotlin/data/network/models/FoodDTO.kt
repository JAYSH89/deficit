package data.network.models

import data.serializers.AmountTypeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.AmountType

@Serializable
data class FoodRequest(
    @SerialName("name")
    val name: String,

    @SerialName("carbs")
    val carbs: Double,

    @SerialName("proteins")
    val proteins: Double,

    @SerialName("fats")
    val fats: Double,

    @SerialName("amount")
    val amount: Double,

    @SerialName("amount_type")
    @Serializable(with = AmountTypeSerializer::class)
    val amountType: AmountType,
)

@Serializable
data class FoodResponse(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("carbs")
    val carbs: Double,

    @SerialName("proteins")
    val proteins: Double,

    @SerialName("fats")
    val fats: Double,

    @SerialName("amount")
    val amount: Double,

    @SerialName("amount_type")
    @Serializable(with = AmountTypeSerializer::class)
    val amountType: AmountType,
)
