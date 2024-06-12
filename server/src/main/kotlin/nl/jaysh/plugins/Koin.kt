package nl.jaysh.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import models.authentication.JwtConfig
import nl.jaysh.core.di.appModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    val jwtConfig = JwtConfig(
        secret = getConfigProperty(path = "jwt.secret"),
        issuer = getConfigProperty(path = "jwt.issuer"),
        audience = getConfigProperty(path = "jwt.audience"),
        realm = getConfigProperty(path = "jwt.realm"),
    )

    install(Koin) {
        slf4jLogger()
        modules(appModule(jwtConfig))
    }
}

private fun Application.getConfigProperty(path: String): String = environment.config
    .property(path)
    .getString()
