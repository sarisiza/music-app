package com.example.musicapp.utils

import com.example.musicapp.view.adapters.SongsListAdapter

sealed class ViewType{

    data class ARTIST(val artist: String): ViewType()

    data class SONGS_DATA(val songsAdapter: SongsListAdapter): ViewType()

}
