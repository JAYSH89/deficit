package models

enum class ActivityFactor {
    SEDENTARY,
    LIGHT_ACTIVITY,
    MODERATE_ACTIVITY,
    VIGOROUS_ACTIVITY;

    companion object {
        @Throws(kotlin.Exception::class)
        fun fromString(value: String): ActivityFactor = entries
            .firstOrNull { activityFactor -> value == activityFactor.toString() }
            ?: throw Exception(message = "Invalid ActivityFactor: $value")
    }
}