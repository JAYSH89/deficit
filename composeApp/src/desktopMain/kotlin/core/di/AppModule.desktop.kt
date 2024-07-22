package core.di

import app.cash.sqldelight.db.SqlDriver
import core.data.local.db.DriverFactory
import feature.food.presentation.FoodViewModel
import nl.jaysh.database.Database
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> { DriverFactory().createDriver() }
    single<Database> { Database(get()) }
    viewModelOf(::FoodViewModel)
}