package com.x.projectxx.data.identity.di

import com.x.projectxx.data.identity.IdentityRepository
import com.x.projectxx.data.identity.IdentityService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object IdentityModule {

    @Provides
    @Singleton
    fun providesIdentifyRepository(identityService: IdentityService): IdentityRepository = identityService
}