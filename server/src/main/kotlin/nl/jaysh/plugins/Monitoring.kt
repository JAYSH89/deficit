package nl.jaysh.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.request.path
import org.slf4j.event.Level.*

fun Application.configureMonitoring() {
    install(CallLogging) {
        level = INFO
        filter { call -> call.request.path().startsWith("/") }
    }
}
