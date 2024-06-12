package data.network.models

import data.serializers.ActivityFactorSerializer
import data.serializers.GenderSerializer
import data.serializers.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.ActivityFactor
import models.Gender
import models.user.UserProfile

@Serializable
data class UserProfileRequest(
    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    @SerialName("date_of_birth")
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateOfBirth: LocalDateTime,

    @SerialName("height")
    val height: Double,

    @SerialName("gender")
    @Serializable(with = GenderSerializer::class)
    val gender: Gender,

    @SerialName("activity_factor")
    @Serializable(with = ActivityFactorSerializer::class)
    val activityFactor: ActivityFactor,
) {
    fun toUserProfile(): UserProfile = UserProfile(
        firstName = firstName,
        lastName = lastName,
        dateOfBirth = dateOfBirth,
        height = height,
        gender = gender,
        activityFactor = activityFactor,
    )
}

@Serializable
data class UserProfileResponse(
    @SerialName("first_name")
    val firstName: String? = null,

    @SerialName("last_name")
    val lastName: String? = null,

    @SerialName("date_of_birth")
    @Serializable(with = LocalDateTimeSerializer::class)
    val dateOfBirth: LocalDateTime,

    @SerialName("height")
    val height: Double,

    @SerialName("gender")
    @Serializable(with = GenderSerializer::class)
    val gender: Gender,

    @SerialName("activity_factor")
    @Serializable(with = ActivityFactorSerializer::class)
    val activityFactor: ActivityFactor,
) {
    companion object {
        fun fromUserProfile(userProfile: UserProfile): UserProfileResponse = UserProfileResponse(
            firstName = userProfile.firstName,
            lastName = userProfile.lastName,
            dateOfBirth = userProfile.dateOfBirth,
            height = userProfile.height,
            gender = userProfile.gender,
            activityFactor = userProfile.activityFactor,
        )
    }
}
