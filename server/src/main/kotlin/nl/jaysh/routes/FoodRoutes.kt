package nl.jaysh.routes

import data.network.models.FoodRequest
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
import models.food.Food
import nl.jaysh.core.principalId
import nl.jaysh.services.FoodService
import org.koin.ktor.ext.inject
import java.util.UUID

fun Route.food() {
    val foodService by inject<FoodService>()

    route("/api/food") {
        authenticate {
            get {
                call.principalId()?.let { userId ->
                    val foods = foodService.getAllFood(userId = userId);
                    call.respond(HttpStatusCode.OK, foods)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            get("/{id}") {
                val requestParam = call.parameters["id"]
                    ?.toString()
                    ?: return@get call.respond(HttpStatusCode.BadRequest)

                val foodId = UUID.fromString(requestParam)
                call.principalId()?.let { userId ->
                    val food = foodService.findById(foodId = foodId, userId = userId)

                    if (food == null)
                        call.respond(HttpStatusCode.NotFound)
                    else
                        call.respond(HttpStatusCode.OK, food)

                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            post {
                val createFood = call.receive<FoodRequest>()

                call.principalId()?.let { userId ->
                    val createdFood = foodService.save(food = createFood, userId = userId)
                    call.respond(HttpStatusCode.Created, createdFood)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            put {
                val updateFood = call.receive<FoodRequest>()

                call.principalId()?.let { userId ->
                    val updatedFood = foodService.updateFood(food = updateFood, userId = userId)
                    call.respond(HttpStatusCode.OK, updatedFood)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            delete("/{id}") {
                val requestParam = call.parameters["id"]
                    ?.toString()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest)

                val foodId = UUID.fromString(requestParam)

                call.principalId()?.let { userId ->
                    foodService.deleteFood(foodId = foodId, userId = userId)
                    call.respond(HttpStatusCode.NoContent)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}
