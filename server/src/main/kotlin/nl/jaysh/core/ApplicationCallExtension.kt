package nl.jaysh.core

import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import nl.jaysh.services.JwtService
import java.util.UUID

fun ApplicationCall.principalId(): UUID? = try {
    val raw = principal<JWTPrincipal>()
        ?.payload
        ?.getClaim(JwtService.ID_CLAIM)
        ?.asString()

    UUID.fromString(raw)
} catch (e: IllegalArgumentException) {
    println("error parsing JwtPrincipal: $e")
    null
}
