package nl.jaysh.data.db

import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import models.authentication.RefreshToken
import models.user.User
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.upsert
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

fun TokenTable.getRefreshToken(token: String): RefreshToken? = innerJoin(UserTable)
    .selectAll()
    .where { TokenTable.token eq token }
    .mapNotNull { row ->
        val user = row.toUser()
        row.toRefreshToken(user)
    }
    .singleOrNull()

fun TokenTable.insert(token: RefreshToken, userId: UUID): RefreshToken {
    val result = upsert {
        it[TokenTable.user] = userId
        it[TokenTable.token] = token.token
        it[TokenTable.issuedAt] = token.issuedAt.toJavaLocalDateTime()
        it[TokenTable.expiresAt] = token.expiresAt.toJavaLocalDateTime()
    }
    check(result.insertedCount == 1)

    val savedToken = getRefreshToken(token = token.token)
    requireNotNull(savedToken)

    return savedToken
}

fun TokenTable.delete(userId: UUID) {
    val rowsChanged = deleteWhere { TokenTable.user eq userId }
    check(rowsChanged == 1)
}


fun ResultRow.toRefreshToken(user: User): RefreshToken = RefreshToken(
    token = this[TokenTable.token],
    issuedAt = this[TokenTable.issuedAt].toKotlinLocalDateTime(),
    expiresAt = this[TokenTable.expiresAt].toKotlinLocalDateTime(),
    user = user,
)
