package nl.jaysh.services

import data.network.models.UserProfileResponse
import data.network.models.UserResponse
import models.user.User
import models.user.UserProfile
import nl.jaysh.data.repository.ProfileRepository
import nl.jaysh.data.repository.UserRepository
import java.util.UUID

class UserService(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
) {
    fun getAll(): List<User> {
        return userRepository.getAll()
    }

    fun findById(userId: UUID): UserResponse? {
        val user = userRepository.findById(userId = userId) ?: return null
        val profile = profileRepository.findById(userId = userId)
        requireNotNull(profile)

        return UserResponse(
            id = user.id,
            email = user.email,
            profile = UserProfileResponse.fromUserProfile(profile),
        )
    }

    fun updateUser(user: User): UserResponse {
        val updatedUser = userRepository.update(user = user)
        val profile = profileRepository.findById(userId = UUID.fromString(updatedUser.id))

        return UserResponse.fromUser(user = updatedUser, userProfile = profile)
    }

    fun deleteUser(userId: UUID) {
        userRepository.delete(userId = userId)
    }

    fun saveProfile(profile: UserProfile, userId: UUID): UserProfileResponse {
        val newProfile = profileRepository.save(profile = profile, userId = userId)
        return UserProfileResponse.fromUserProfile(userProfile = newProfile)
    }

    fun deleteProfile(userId: UUID) {
        profileRepository.delete(userId = userId)
    }
}
