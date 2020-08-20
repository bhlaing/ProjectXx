package com.x.projectxx.application.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.x.projectxx.application.authentication.LoginManager
import com.x.projectxx.application.authentication.LoginManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideLoginManager(loginManagerImpl: LoginManagerImpl): LoginManager = loginManagerImpl

    @Singleton
    @Provides
    fun provideCloudFirebaseFirestoreDatabase(): FirebaseFirestore = Firebase.firestore
}