package com.example.musicapp.rest

import com.example.musicapp.model.domain.Song
import com.example.musicapp.model.domain.mapToSong
import com.example.musicapp.utils.FailureResponse
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.NullSongsResponse
import com.example.musicapp.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MusicRepository {

    /**
     * Function to get all songs
     */
    fun getAllSongs(genre: Genres): Flow<UIState>

}

class MusicRepositoryImpl @Inject constructor(
    private val musicApi: MusicApi
): MusicRepository{

    /**
     * Method to retrieve songs from the API
     * This will also create the domain
     */
    override fun getAllSongs(genre: Genres): Flow<UIState> = flow {
        emit(UIState.LOADING)
        try{
            val response = musicApi.getSongsList(genre.genre)
            if(response.isSuccessful){
                response.body()?.let {response ->
                    val songsList: MutableList<Song> = mutableListOf()
                    response.songItems.forEach {
                        songsList.add(it.mapToSong(genre.genre))
                    }
                    emit(UIState.SUCCESS(songsList))
                } ?: throw NullSongsResponse()
            }
            else throw FailureResponse(response.errorBody()?.string())
        }catch (e: Exception){
            emit(UIState.ERROR(e))
        }
    }

}


