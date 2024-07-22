package core.data.local.db.entity

import core.model.food.AmountType
import core.model.food.Food
import nl.jaysh.database.FoodEntity

fun FoodEntity.toFood(): Food = Food(
    id = this.id,
    name = this.name,
    carbs = this.carbs,
    proteins = this.proteins,
    fats = this.fats,
    calories = this.calories,
    amount = this.amount,
    amountType = AmountType.valueOf(this.amount_type),
)
