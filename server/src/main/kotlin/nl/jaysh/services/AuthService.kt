package nl.jaysh.services

import data.network.models.AuthRequest
import data.network.models.AuthResponse
import kotlinx.datetime.toKotlinLocalDateTime
import models.authentication.RefreshToken
import models.user.User
import nl.jaysh.core.ServerConfig.ACCESS_TOKEN_EXPIRATION
import nl.jaysh.core.ServerConfig.REFRESH_TOKEN_EXPIRATION
import nl.jaysh.data.repository.TokenRepository
import nl.jaysh.data.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDateTime
import java.util.UUID

class AuthService(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val jwtService: JwtService,
) {
    fun register(request: AuthRequest): AuthResponse {
        val hashedPassword = BCrypt.hashpw(request.password, BCrypt.gensalt())

        val user = userRepository.insert(email = request.email, password = hashedPassword)
        val accessToken = jwtService.createAccessToken(user = user)
        val refreshToken = saveRefreshToken(user = user)

        return AuthResponse(
            tokenType = "Bearer",
            accessToken = accessToken,
            refreshToken = refreshToken.token,
            expiresIn = ACCESS_TOKEN_EXPIRATION,
        )
    }

    fun login(request: AuthRequest): AuthResponse? {
        val user = userRepository.findByEmail(email = request.email) ?: return null
        if (!BCrypt.checkpw(request.password, user.password)) return null

        val accessToken = jwtService.createAccessToken(user = user)
        val refreshToken = saveRefreshToken(user = user)

        return AuthResponse(
            tokenType = "Bearer",
            accessToken = accessToken,
            refreshToken = refreshToken.token,
            expiresIn = ACCESS_TOKEN_EXPIRATION,
        )
    }

    fun refresh(refreshToken: String): String? {
        val decodedRefreshToken = jwtService.verifyRefreshToken(refreshToken)
        val token = tokenRepository.findToken(refreshToken)

        if (decodedRefreshToken != null && token != null) {
            val id = decodedRefreshToken.getClaim(JwtService.ID_CLAIM).asString()

            if (id == token.user.id.toString())
                return jwtService.createAccessToken(token.user)

            return null
        }

        return null
    }

    private fun saveRefreshToken(user: User): RefreshToken {
        val refreshToken = RefreshToken(
            token = jwtService.createRefreshToken(user = user),
            issuedAt = LocalDateTime.now().toKotlinLocalDateTime(),
            expiresAt = LocalDateTime
                .now()
                .plusSeconds(REFRESH_TOKEN_EXPIRATION / 1_000)
                .toKotlinLocalDateTime(),
            user = user,
        )

        return tokenRepository.save(refreshToken, UUID.fromString(user.id))
    }
}