package nl.jaysh.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.server.routing.routing
import nl.jaysh.routes.authentication
import nl.jaysh.routes.food
import nl.jaysh.routes.journal
import nl.jaysh.routes.user
import nl.jaysh.routes.weight

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(
                text = "500: $cause",
                status = HttpStatusCode.InternalServerError,
            )
        }
    }

    routing {
        authentication()
        user()
        food()
        journal()
        weight()
    }
}