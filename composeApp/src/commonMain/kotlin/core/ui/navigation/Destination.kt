package core.ui.navigation

object Destination {
    const val HOME = "home"
    const val OVERVIEW = "overview"
    const val FOOD_OVERVIEW = "food_overview"
    const val CREATE_FOOD = "create_food"
    const val FOOD_DETAIL = "food_detail"
    const val JOURNAL_OVERVIEW = "journal_overview"
    const val SETTINGS = "settings"

    fun foodDetail(foodId: Long): String = "$FOOD_DETAIL?foodId=$foodId"
}
