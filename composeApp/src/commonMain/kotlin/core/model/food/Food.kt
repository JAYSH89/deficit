package core.model.food

data class Food(
    val id: Long? = null,
    val name: String,
    val carbs: Double,
    val proteins: Double,
    val fats: Double,
    val calories: Double,
    val amount: Double,
    val amountType: AmountType,
)
