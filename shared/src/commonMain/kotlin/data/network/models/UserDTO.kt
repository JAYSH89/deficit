package data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.user.User

@Serializable
data class UserRequest(
    @SerialName("id")
    val id: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,
) {
    fun toUser(): User = User(
        id = this.id,
        email = this.email,
        password = this.password,
    )
}


@Serializable
data class UserResponse(
    @SerialName("id")
    val id: String,

    @SerialName("email")
    val email: String,

    @SerialName("profile")
    val profile: UserProfileResponse? = null,
) {
    companion object {
        fun fromUser(user: User): UserResponse = UserResponse(
            id = user.id,
            email = user.email,
        )
    }
}
