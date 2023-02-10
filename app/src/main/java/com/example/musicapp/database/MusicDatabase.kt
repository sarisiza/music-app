package com.example.musicapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicapp.model.domain.Song

@Database(
    entities = [
        Song::class
    ],
    version = 1
)
abstract class MusicDatabase: RoomDatabase() {
    abstract fun getMusicDAO(): MusicDAO
}