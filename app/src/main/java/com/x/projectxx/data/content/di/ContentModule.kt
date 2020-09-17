package com.x.projectxx.data.content.di

import com.x.projectxx.data.content.ContentRepository
import com.x.projectxx.data.content.ContentService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ContentModule {

    @Provides
    @Singleton
    fun providesContentRepository(contentService: ContentService): ContentRepository = contentService
}