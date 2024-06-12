package models

enum class AmountType {
    UNIT,
    METRIC;

    companion object {
        @Throws(kotlin.Exception::class)
        fun fromString(value: String): AmountType = AmountType.entries
            .firstOrNull { type -> value == type.toString() }
            ?: throw Exception(message = "Invalid AmountType $value")
    }
}
