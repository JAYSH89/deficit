package core.di

import feature.food.data.datasource.FakeFoodDataSource
import feature.food.data.datasource.FoodDataSourceImpl
import feature.food.data.datasource.FoodDatasource
import feature.food.data.repository.FoodRepositoryImpl
import feature.food.domain.FoodRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
    singleOf(::FakeFoodDataSource).bind<FoodDatasource>()
//    singleOf(::FoodDataSourceImpl).bind<FoodDatasource>()
    singleOf(::FoodRepositoryImpl).bind<FoodRepository>()
}