package com.example.musicapp.utils

import com.example.musicapp.model.SongItem

sealed class UIState(){

    object LOADING: UIState()

    data class SUCCESS(val response: SongItem): UIState()

    data class ERROR(val error: Exception): UIState()

}
