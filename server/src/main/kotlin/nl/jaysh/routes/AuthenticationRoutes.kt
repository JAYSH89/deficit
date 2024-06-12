package nl.jaysh.routes

import data.network.models.AuthRequest
import data.network.models.RefreshTokenRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import nl.jaysh.services.AuthService
import org.koin.ktor.ext.inject

fun Route.authentication() {
    val authService by inject<AuthService>()

    route("/api/auth") {
        post("/register") {
            val request = call.receive<AuthRequest>()
            val response = authService.register(request = request)
            call.respond(HttpStatusCode.Created, response)
        }

        post("/login") {
            val request = call.receive<AuthRequest>()
            val response = authService.login(request = request)

            response?.let { token ->
                call.respond(HttpStatusCode.OK, token)
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }

        post("/refresh") {
            val request = call.receive<RefreshTokenRequest>()
            val accessToken = authService.refresh(refreshToken = request.refreshToken)

            accessToken?.let { token ->
                val response = mapOf("access_token" to token)
                call.respond(HttpStatusCode.OK, response)
            } ?: call.respond(HttpStatusCode.Unauthorized)
        }
    }
}
