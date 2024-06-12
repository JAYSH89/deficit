package models.user

import kotlinx.datetime.LocalDateTime
import models.ActivityFactor
import models.Gender

data class UserProfile(
    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: LocalDateTime,
    val height: Double,
    val gender: Gender,
    val activityFactor: ActivityFactor,
)
