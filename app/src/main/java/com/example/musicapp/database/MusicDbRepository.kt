package com.example.musicapp.database

import androidx.annotation.WorkerThread
import com.example.musicapp.model.domain.Song
import com.example.musicapp.utils.Genres
import kotlinx.coroutines.flow.Flow

/**
 * Implement the database queries
 */
class MusicDbRepository(
    private val musicDAO: MusicDAO
) {

    //get rock songs
    val allRockSongs: Flow<List<Song>> = musicDAO.getSongsByGenre(Genres.ROCK.genre)
    //get pop songs
    val allPopSongs: Flow<List<Song>> = musicDAO.getSongsByGenre(Genres.POP.genre)
    //get classic songs
    val allClassicSongs: Flow<List<Song>> = musicDAO.getSongsByGenre(Genres.CLASSIC.genre)

    //get size of rock list
    val rockListSize: Flow<Int> = musicDAO.getSongsSize(Genres.ROCK.genre)
    //get size of pop list
    val popListSize: Flow<Int> = musicDAO.getSongsSize(Genres.POP.genre)
    //get size of classic list
    val classicListSize: Flow<Int> = musicDAO.getSongsSize(Genres.CLASSIC.genre)

    /**
     * Method to insert a song in the database
     */
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSong(song: Song){
        musicDAO.insertSong(song)
    }

}