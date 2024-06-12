package nl.jaysh.data.db

import kotlinx.datetime.toKotlinLocalDateTime
import models.authentication.RefreshToken
import models.user.User
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID


object TokenTable : Table(name = "refresh_token") {
    val token: Column<String> = text(name = "token").uniqueIndex()
    val issuedAt: Column<LocalDateTime> = datetime(name = "issued_at")
    val expiresAt: Column<LocalDateTime> = datetime(name = "expires_at")

    val user: Column<EntityID<UUID>> = reference(
        name = "user_id",
        refColumn = UserTable.id,
        onDelete = ReferenceOption.CASCADE,
    ).uniqueIndex()

    override val primaryKey = super.primaryKey ?: PrimaryKey(user)
}

fun ResultRow.toRefreshToken(user: User): RefreshToken = RefreshToken(
    token = this[TokenTable.token],
    issuedAt = this[TokenTable.issuedAt].toKotlinLocalDateTime(),
    expiresAt = this[TokenTable.expiresAt].toKotlinLocalDateTime(),
    user = user,
)
