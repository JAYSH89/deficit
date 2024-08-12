package core.di

import app.cash.sqldelight.db.SqlDriver
import core.data.local.db.DriverFactory
import feature.food.FoodDetailViewModel
import feature.food.FoodOverviewViewModel
import feature.journal.JournalOverviewViewModel
import feature.overview.OverviewViewModel
import feature.settings.SettingsViewModel
import nl.jaysh.database.Database
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule = module {
    single<SqlDriver> { DriverFactory().createDriver() }
    single<Database> { Database(get()) }
    viewModelOf(::FoodOverviewViewModel)
    viewModelOf(::FoodDetailViewModel)
    viewModelOf(::JournalOverviewViewModel)
    viewModelOf(::OverviewViewModel)
    viewModelOf(::SettingsViewModel)
}