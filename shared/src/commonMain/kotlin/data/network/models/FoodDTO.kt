package data.network.models

import data.serializers.AmountTypeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.AmountType
import models.food.Food

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
) {
    fun toFood(): Food = Food(
        name = name,
        carbs = carbs,
        proteins = proteins,
        fats = fats,
        amount = amount,
        amountType = amountType,
    )
}

@Serializable
data class FoodResponse(
    @SerialName("id")
    val id: String? = null,

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
) {
    companion object {
        fun fromFood(food: Food): FoodResponse = FoodResponse(
            id = food.id,
            name = food.name,
            carbs = food.carbs,
            proteins = food.proteins,
            fats = food.fats,
            amount = food.amount,
            amountType = food.amountType,
        )
    }
}
