package core.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char

object LocalDateTimeFormat {
    val date = LocalDateTime.Format {
        dayOfMonth()
        char('-')
        monthNumber()
        char('-')
        year()
    }

    val time = LocalDateTime.Format {
        hour()
        char(':')
        minute()
    }
}
