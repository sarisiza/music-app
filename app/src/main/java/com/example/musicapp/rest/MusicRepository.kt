package com.example.musicapp.rest

import com.example.musicapp.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MusicRepository {

    /**
     * Function to get all songs
     */
    fun getAllSongs(genre: String): Flow<UIState>

}

class MusicRepositoryImpl(): MusicRepository{

    /**
     * Method to retrieve songs from the API
     */
    override fun getAllSongs(genre: String): Flow<UIState> = flow {
        emit(UIState.LOADING)
        //todo get songs from api
    }

}

