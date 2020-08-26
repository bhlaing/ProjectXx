package com.x.projectxx.data.contacts.di

import com.x.projectxx.data.contacts.ContactRepository
import com.x.projectxx.data.contacts.ContactService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ContactModule {

    @Provides
    @Singleton
    fun providesContactRepository(contactService: ContactService): ContactRepository = contactService
}