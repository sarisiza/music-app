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

    val rockListSize: Flow<Int> = musicDAO.getSongsSize(Genres.ROCK.genre)
    val popListSize: Flow<Int> = musicDAO.getSongsSize(Genres.POP.genre)
    val classicListSize: Flow<Int> = musicDAO.getSongsSize(Genres.CLASSIC.genre)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSong(song: Song){
        musicDAO.insertSong(song)
    }

}