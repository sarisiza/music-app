package com.example.musicapp.utils

import com.example.musicapp.model.domain.Song

sealed class UIState(){

    object LOADING: UIState()

    data class SUCCESS(val response: List<Song>): UIState()

    data class ERROR(val error: Exception): UIState()

}
