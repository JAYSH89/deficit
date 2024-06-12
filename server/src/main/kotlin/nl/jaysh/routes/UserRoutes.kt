package nl.jaysh.routes

import data.network.models.UserRequest
import data.network.models.UserResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import models.user.UserProfile
import nl.jaysh.core.principalId
import nl.jaysh.services.UserService
import org.koin.ktor.ext.inject

fun Route.user() {
    val userService by inject<UserService>()

    route("/api/user") {
        authenticate {
            get("/me") {
                call.principalId()?.let { userId ->
                    val user = userService.findById(userId = userId)

                    if (user == null)
                        call.respond(HttpStatusCode.NotFound)
                    else
                        call.respond(HttpStatusCode.OK, user)

                } ?: call.respond(HttpStatusCode.BadRequest)
            }
        }

        authenticate {
            put("/me") {
                call.principalId()?.let { userId ->
                    val profile = call.receive<UserProfile>()
                    val updatedProfile = userService.saveProfile(profile = profile, userId = userId)
                    call.respond(HttpStatusCode.OK, updatedProfile)
                } ?: call.respond(HttpStatusCode.BadRequest)
            }
        }

        authenticate {
            put {
                call.principalId()?.let { userId ->
                    val userRequest = call.receive<UserRequest>()

                    if (userRequest.id != userId.toString()) {
                        call.respond(HttpStatusCode.BadRequest)
                    } else {
                        val updatedUser = userService.updateUser(user = userRequest.toUser())
                        call.respond(HttpStatusCode.OK, updatedUser)
                    }
                } ?: call.respond(HttpStatusCode.BadRequest)
            }
        }

        authenticate {
            delete {
                call.principalId()?.let { userId ->
                    val user = userService.findById(userId = userId)

                    if (user?.id != userId.toString()) {
                        call.respond(HttpStatusCode.BadRequest)
                    } else {
                        userService.deleteUser(userId = userId)
                        call.respond(HttpStatusCode.NoContent)
                    }
                } ?: call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
