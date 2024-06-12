package models.food

import models.AmountType

data class Food(
    val id: Long,
    val name: String,
    val carbs: Double,
    val proteins: Double,
    val fats: Double,
    val amount: Double,
    val amountType: AmountType,
)
