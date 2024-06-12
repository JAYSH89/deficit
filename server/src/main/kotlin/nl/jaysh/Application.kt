package nl.jaysh

import io.ktor.server.application.*
import io.ktor.server.cio.EngineMain
import nl.jaysh.core.DatabaseFactory
import nl.jaysh.plugins.configureKoin
import nl.jaysh.plugins.configureMonitoring
import nl.jaysh.plugins.configureRouting
import nl.jaysh.plugins.configureSecurity
import nl.jaysh.plugins.configureSerialization

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()
    configureKoin()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting()
}