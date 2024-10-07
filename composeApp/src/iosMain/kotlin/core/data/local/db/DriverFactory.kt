package core.data.local.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import nl.jaysh.database.Database

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = Database.Schema,
            name = "deficit.db",
            onConfiguration = { config: DatabaseConfiguration ->
                val extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
                config.copy(extendedConfig = extendedConfig)
            }
        )
    }
}