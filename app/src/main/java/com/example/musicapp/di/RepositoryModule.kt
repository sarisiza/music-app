package com.example.musicapp.di

import com.example.musicapp.rest.MusicRepository
import com.example.musicapp.rest.MusicRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    /**
     * Method that provides the Music Repository
     */
    @Binds
    abstract fun provideMusicRepository(
        musicRepositoryImpl: MusicRepositoryImpl
    ): MusicRepository

}