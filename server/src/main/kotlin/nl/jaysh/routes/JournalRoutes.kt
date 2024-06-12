package nl.jaysh.routes

import data.network.models.JournalEntryRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import nl.jaysh.core.principalId
import nl.jaysh.services.JournalService
import org.koin.ktor.ext.inject
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

fun Route.journal() {
    val journalService by inject<JournalService>()

    route("/api/journal") {
        authenticate {
            get("/{id}") {
                val requestParam = call.parameters["id"]
                    ?.toString()
                    ?: return@get call.respond(HttpStatusCode.BadRequest)

                val journalEntryId = UUID.fromString(requestParam)

                call.principalId()?.let { userId ->
                    val journalEntry = journalService.findById(journalEntryId = journalEntryId, userId = userId)

                    if (journalEntry == null)
                        call.respond(HttpStatusCode.NotFound)
                    else
                        call.respond(HttpStatusCode.OK, journalEntry)

                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            get {
                call.principalId()?.let { userId ->
                    val startDateParam = call.request.queryParameters["startDate"]
                    val endDateParam = call.request.queryParameters["endDate"]

                    if (startDateParam != null && endDateParam != null) {
                        val startDate = LocalDate.parse(startDateParam, DateTimeFormatter.ISO_DATE)
                        val endDate = LocalDate.parse(endDateParam, DateTimeFormatter.ISO_DATE)

                        val result = journalService.getBetween(
                            startDate = LocalDateTime.of(startDate, LocalTime.MIN),
                            endDate = LocalDateTime.of(endDate, LocalTime.MIN),
                            userId = userId,
                        )
                        call.respond(result)

                        return@get
                    }

                    val result = journalService.getAllJournalEntries(userId = userId)
                    call.respond(result)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            get("/summary") {
                call.principalId()?.let { userId ->
                    val startDateParam = call.request.queryParameters["startDate"]
                    val endDateParam = call.request.queryParameters["endDate"]

                    if (startDateParam != null && endDateParam != null) {
                        val startDate = LocalDateTime.parse(startDateParam, DateTimeFormatter.ISO_DATE_TIME)
                        val endDate = LocalDateTime.parse(endDateParam, DateTimeFormatter.ISO_DATE_TIME)

                        val overview = journalService.getSummaryBetween(
                            startDate = startDate,
                            endDate = endDate,
                            userId = userId,
                        )
                        call.respond(HttpStatusCode.OK, overview)
                    } else {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            post {
                val createJournalEntry = call.receive<JournalEntryRequest>()

                call.principalId()?.let { userId ->
                    val createdFood = journalService.save(request = createJournalEntry, userId = userId)
                    call.respond(HttpStatusCode.Created, createdFood)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        authenticate {
            delete("/{id}") {
                val requestParam = call.parameters["id"]
                    ?.toString()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest)

                val journalEntryId = UUID.fromString(requestParam)

                call.principalId()?.let { userId ->
                    journalService.delete(journalEntryId = journalEntryId, userId = userId)
                    call.respond(HttpStatusCode.NoContent)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}
