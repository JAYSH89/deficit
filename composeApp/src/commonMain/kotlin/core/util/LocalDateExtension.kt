package core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

fun LocalDate.Companion.now(): LocalDate {
    return Clock.System.todayIn(TimeZone.currentSystemDefault())
}
