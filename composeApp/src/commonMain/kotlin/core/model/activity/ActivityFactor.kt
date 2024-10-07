package core.model.activity

enum class ActivityFactor(val factor: Double) {
    SEDENTARY(factor = 1.2),
    LIGHT_EXERCISE(factor = 1.375),
    MODERATE_EXERCISE(factor = 1.55),
    HARD_EXERCISE(factor = 1.725),
    VERY_HARD_EXERCISE(factor = 1.9),
}
