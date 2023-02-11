package com.example.musicapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicapp.model.domain.Song

/**
 * Create Room DB
 */
@Database(
    entities = [
        Song::class
    ],
    version = 1
)
abstract class MusicDatabase: RoomDatabase() {

    /**
     * Returns the DAO
     */
    abstract fun getMusicDAO(): MusicDAO
}