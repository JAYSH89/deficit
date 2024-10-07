package core.data.local.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import nl.jaysh.database.Database
import java.util.Properties

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(
            url = JdbcSqliteDriver.IN_MEMORY,
            properties = Properties().apply { put("foreign_keys", "true") }
        )
        Database.Schema.create(driver)
        return driver
    }
}