package com.example.musicapp.utils

import com.example.musicapp.model.domain.Song

/**
 * Defines the state of the LiveData
 */
sealed class UIState(){

    object LOADING: UIState() //Loading

    data class SUCCESS(val response: List<Song>): UIState() //Success

    data class ERROR(val error: Exception): UIState() //Error

}
