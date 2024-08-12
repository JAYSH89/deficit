package core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import core.data.local.db.datasource.food.FoodDataSourceImpl
import core.data.local.db.datasource.food.FoodDatasource
import core.data.local.db.datasource.journal.JournalDataSource
import core.data.local.db.datasource.journal.JournalDataSourceImpl
import core.data.local.db.datasource.weight.WeightDataSource
import core.data.local.db.datasource.weight.WeightDataSourceImpl
import core.data.repository.FoodRepositoryImpl
import core.data.repository.FoodRepository
import core.data.repository.JournalRepositoryImpl
import core.data.repository.JournalRepository
import core.data.repository.ProfileRepository
import core.data.repository.ProfileRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    singleOf(::DefaultDispatcherProvider).bind<DispatcherProvider>()

    singleOf(::FoodDataSourceImpl).bind<FoodDatasource>()
    singleOf(::JournalDataSourceImpl).bind<JournalDataSource>()

    singleOf(::FoodRepositoryImpl).bind<FoodRepository>()
    singleOf(::JournalRepositoryImpl).bind<JournalRepository>()

    singleOf(::WeightDataSourceImpl).bind<WeightDataSource>()
    singleOf(::ProfileRepositoryImpl).bind<ProfileRepository>()
}