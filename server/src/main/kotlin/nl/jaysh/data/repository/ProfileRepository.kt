package nl.jaysh.data.repository

import models.user.UserProfile
import nl.jaysh.data.db.ProfileTable
import nl.jaysh.data.db.delete
import nl.jaysh.data.db.findById
import nl.jaysh.data.db.insert
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class ProfileRepository {

    init {
        transaction { SchemaUtils.create(ProfileTable) }
    }

    fun findById(userId: UUID): UserProfile? = transaction {
        ProfileTable.findById(userId = userId)
    }

    fun save(profile: UserProfile, userId: UUID): UserProfile = transaction {
        ProfileTable.insert(profile = profile, userId = userId)
    }

    fun delete(userId: UUID) = transaction {
        ProfileTable.delete(userId = userId)
    }
}