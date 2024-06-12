package models

enum class AmountType {
    UNIT,
    METRIC,
    UNKNOWN;

    companion object {
        fun fromString(value: String): AmountType = AmountType.entries
            .firstOrNull { type -> value == type.toString() }
            ?: UNKNOWN
    }
}
