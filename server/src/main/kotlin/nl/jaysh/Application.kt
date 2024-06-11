package nl.jaysh

import Greeting
import io.ktor.server.application.*
import io.ktor.server.cio.EngineMain
import io.ktor.server.response.*
import io.ktor.server.routing.*
import nl.jaysh.core.DatabaseFactory

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init()

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}