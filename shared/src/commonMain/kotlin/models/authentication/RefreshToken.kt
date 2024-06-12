package models.authentication

import kotlinx.datetime.LocalDateTime
import models.user.User

data class RefreshToken(
    val token: String,
    val issuedAt: LocalDateTime,
    val expiresAt: LocalDateTime,
    val user: User,
)
