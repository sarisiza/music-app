package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.model.domain.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDAO {

    @Query("SELECT * FROM song")
    suspend fun getSongs(): List<Song>

    @Query("SELECT * FROM song WHERE genre LIKE genreName")
    suspend fun getSongsByGenre(genreName: String): Flow<List<Song>>

    @Insert(
        entity = Song::class,
        onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongList(vararg songs: List<Song>)

}