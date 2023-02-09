package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.model.domain.Song

@Dao
interface MusicDAO {

    @Query("SELECT * FROM song")
    suspend fun getSongs(): List<Song>

    @Query("SELECT * FROM song WHERE name LIKE: songName LIMIT 1")
    suspend fun getSongByName(songName: String): Song

    @Insert(
        entity = Song::class,
        onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(vararg people: Song)

}