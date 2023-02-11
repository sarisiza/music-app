package com.example.musicapp.di

import android.content.Context
import androidx.room.Room
import com.example.musicapp.database.MusicDAO
import com.example.musicapp.database.MusicDatabase
import com.example.musicapp.database.MusicDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Dependency injection for database
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule() {

    /**
     * Creates a single instance of the database
     */
    @Provides
    fun providesMusicDatabase(
        @ApplicationContext context: Context
    ): MusicDatabase =
        Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            "music-db"
        ).build()

    /**
     * Provides the DAO of the database
     */
    @Provides
    fun providesMusicDAO(
        musicDatabase: MusicDatabase
    ): MusicDAO =
        musicDatabase.getMusicDAO()

    /**
     * Provides the DB Repository
     */
    @Provides
    fun providesMusicDBRepository(
        musicDAO: MusicDAO
    ): MusicDbRepository =
        MusicDbRepository(musicDAO)


}