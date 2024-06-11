package nl.jaysh.core

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    private val url = System.getenv("DB_URL")
    private val user = System.getenv("DB_USER")
    private val password = System.getenv("DB_PASSWORD")

    private val hikari: HikariDataSource
        get() {
            val config = HikariConfig()
                .apply {
                    driverClassName = "org.postgresql.Driver"
                    jdbcUrl = url
                    username = user
                    password = DatabaseFactory.password
                    maximumPoolSize = 3
                    isAutoCommit = false
                    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                }
                .also { config -> config.validate() }

            return HikariDataSource(config)
        }

    fun init() {
        Database.connect(hikari)
        val flyway = Flyway.configure().dataSource(url, user, password).load()
        flyway.migrate()
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction { block() }
    }
}