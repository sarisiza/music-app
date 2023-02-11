package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.model.domain.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDAO {


    @Query("SELECT * FROM song WHERE genre = :genreName")
    fun getSongsByGenre(genreName: String): Flow<List<Song>>

    @Query("SELECT COUNT(*) FROM song WHERE genre = :genreName")
    fun getSongsSize(genreName: String): Flow<Int>

    @Insert(
        entity = Song::class,
        onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(vararg songs: Song)

}