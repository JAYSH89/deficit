package nl.jaysh.routes

import data.network.models.WeightRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import models.weight.Weight
import nl.jaysh.core.principalId
import nl.jaysh.services.WeightService
import org.koin.ktor.ext.inject
import java.util.UUID

fun Route.weight() {
    val weightService by inject<WeightService>()

    route("/api/weight") {
        authenticate {
            get {
                call.principalId()?.let { userId ->
                    val weights = weightService.getAll(userId = userId)
                    call.respond(HttpStatusCode.OK, weights)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            post {
                val createWeight = call.receive<WeightRequest>()

                call.principalId()?.let { userId ->
                    val createdWeight = weightService.insert(weight = createWeight, userId = userId)
                    call.respond(HttpStatusCode.Created, createdWeight)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            put {
                val createWeight = call.receive<WeightRequest>()

                call.principalId()?.let { userId ->
                    val updatedWeight = weightService.update(weight = createWeight, userId = userId)
                    call.respond(HttpStatusCode.OK, updatedWeight)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            delete("/{id}") {
                val requestParam = call.parameters["id"]
                    ?.toString()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest)

                val weightId = UUID.fromString(requestParam)

                call.principalId()?.let { userId ->
                    weightService.delete(weightId = weightId, userId = userId)
                    call.respond(HttpStatusCode.NoContent)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}
