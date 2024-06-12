package models

enum class Gender {
    MALE,
    FEMALE;

    companion object {
        @Throws(kotlin.Exception::class)
        fun fromString(value: String): Gender = entries
            .firstOrNull { gender -> value == gender.toString() }
            ?: throw Exception(message = "Invalid Gender: $value")
    }
}