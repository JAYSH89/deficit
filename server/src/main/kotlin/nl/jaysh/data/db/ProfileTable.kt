package nl.jaysh.data.db

import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import models.ActivityFactor
import models.Gender
import models.user.UserProfile
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

object ProfileTable : Table(name = "profile") {
    val firstName: Column<String?> = varchar(name = "first_name", length = 50).nullable()
    val lastName: Column<String?> = varchar(name = "last_name", length = 50).nullable()
    val dateOfBirth: Column<LocalDateTime> = datetime(name = "date_of_birth")
    val height: Column<Double> = double(name = "height")
    val gender: Column<String> = varchar(name = "gender", length = 50)
    val activityFactor: Column<String> = varchar(name = "activity_factor", length = 50)

    val user: Column<EntityID<UUID>> = reference(
        name = "user_id",
        refColumn = UserTable.id,
        onDelete = ReferenceOption.CASCADE,
    ).uniqueIndex()

    override val primaryKey = super.primaryKey ?: PrimaryKey(user)
}

fun ProfileTable.findById(userId: UUID): UserProfile? = selectAll()
    .where { user eq userId }
    .map(ResultRow::toProfile)
    .singleOrNull()

fun ProfileTable.insert(profile: UserProfile, userId: UUID): UserProfile {
    val result = upsert {
        it[user] = userId
        it[firstName] = profile.firstName
        it[lastName] = profile.lastName
        it[dateOfBirth] = profile.dateOfBirth.toJavaLocalDateTime()
        it[height] = profile.height
        it[gender] = profile.gender.toString()
        it[activityFactor] = profile.activityFactor.toString()
    }
    check(result.insertedCount == 1)

    val updatedProfile = findById(userId = userId)
    requireNotNull(updatedProfile)

    return updatedProfile
}

fun ProfileTable.delete(userId: UUID) {
    val rowsChanged = deleteWhere { user eq userId }
    check(rowsChanged == 1)
}

fun ResultRow.toProfile(): UserProfile = UserProfile(
    firstName = this[ProfileTable.firstName],
    lastName = this[ProfileTable.lastName],
    dateOfBirth = this[ProfileTable.dateOfBirth].toKotlinLocalDateTime(),
    height = this[ProfileTable.height],
    gender = Gender.fromString(this[ProfileTable.gender]),
    activityFactor = ActivityFactor.fromString(this[ProfileTable.activityFactor]),
)
