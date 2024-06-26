package nl.jaysh.core.di

import models.authentication.JwtConfig
import nl.jaysh.data.repository.ProfileRepository
import nl.jaysh.data.repository.TokenRepository
import nl.jaysh.data.repository.UserRepository
import nl.jaysh.data.repository.FoodRepository
import nl.jaysh.data.repository.JournalRepository
import nl.jaysh.data.repository.WeightRepository
import nl.jaysh.services.AuthService
import nl.jaysh.services.FoodService
import nl.jaysh.services.JournalService
import nl.jaysh.services.JwtService
import nl.jaysh.services.UserService
import nl.jaysh.services.WeightService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun appModule(jwtConfig: JwtConfig) = module {
    single { jwtConfig }

    singleOf(::TokenRepository)
    singleOf(::ProfileRepository)
    singleOf(::UserRepository)
    singleOf(::FoodRepository)
    singleOf(::JournalRepository)
    singleOf(::WeightRepository)

    singleOf(::JwtService)
    singleOf(::AuthService)
    singleOf(::UserService)
    singleOf(::FoodService)
    singleOf(::JournalService)
    singleOf(::WeightService)
}