package com.example.musicapp.rest

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
     */
    override fun getAllSongs(genre: Genres): Flow<UIState> = flow {
        emit(UIState.LOADING)
        try{
            val response = musicApi.getSongsList(genre.genre)
            if(response.isSuccessful){
                response.body()?.let {
                    emit(UIState.SUCCESS(it))
                } ?: throw NullSongsResponse()
            }
            else throw FailureResponse(response.errorBody()?.string())
        }catch (e: Exception){
            emit(UIState.ERROR(e))
        }
    }

}


