package com.example.musicapp.database

import androidx.annotation.WorkerThread
import com.example.musicapp.model.domain.Song
import com.example.musicapp.utils.Genres
import kotlinx.coroutines.flow.Flow

class MusicDbRepository(
    private val musicDAO: MusicDAO
) {

    val allRockSongs: Flow<List<Song>> = musicDAO.getSongsByGenre(Genres.ROCK.genre)
    val allPopSongs: Flow<List<Song>> = musicDAO.getSongsByGenre(Genres.POP.genre)
    val allClassicSongs: Flow<List<Song>> = musicDAO.getSongsByGenre(Genres.CLASSIC.genre)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAllSongs(songsList: List<Song>){
        musicDAO.insertSongList(songsList)
    }

}