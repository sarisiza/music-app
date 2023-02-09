package com.example.musicapp.database

import androidx.room.Database
import com.example.musicapp.model.domain.Song

@Database(
    entities = [
        Song::class
    ],
    version = 1
)
abstract class MusicDatabase {
    abstract fun getMusicDAO(): MusicDAO
}