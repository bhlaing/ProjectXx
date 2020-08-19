package com.x.projectxx.data.identity

import com.x.projectxx.data.identity.model.UserProfile

interface IdentityRepository {
    suspend fun getUserProfile(userId: String): UserProfile?
    suspend fun createUserProfile(userProfile: UserProfile): UserProfile
}