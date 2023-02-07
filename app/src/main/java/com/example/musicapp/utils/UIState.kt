package com.example.musicapp.utils

import com.example.musicapp.model.MusicResponse

sealed class UIState(){

    object LOADING: UIState()

    data class SUCCESS(val response: MusicResponse): UIState()

    data class ERROR(val error: Exception): UIState()

}
