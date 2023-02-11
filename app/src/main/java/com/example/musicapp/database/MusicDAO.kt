package com.example.musicapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.musicapp.model.domain.Song
import kotlinx.coroutines.flow.Flow

/**
 * Define methods for each database query
 */
@Dao
interface MusicDAO {

    /**
     * Get a list of songs by genre from the database
     */
    @Query("SELECT * FROM song WHERE genre = :genreName")
    fun getSongsByGenre(genreName: String): Flow<List<Song>>

    /**
     * Get the count of the items in the table by genre
     */
    @Query("SELECT COUNT(*) FROM song WHERE genre = :genreName")
    fun getSongsSize(genreName: String): Flow<Int>

    /**
     * Insert a song into the database
     */
    @Insert(
        entity = Song::class,
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertSong(vararg songs: Song)

}