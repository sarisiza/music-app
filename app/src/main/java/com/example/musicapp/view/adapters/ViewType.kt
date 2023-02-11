package com.example.musicapp.view.adapters

/**
 * Defines the type of data that the ArtistsSongAdapter is using
 */
sealed class ViewType{

    /**
     * Artist names
     */
    data class ARTIST(val artist: String): ViewType()

    /**
     * Songs adapters
     */
    data class SONGS_DATA(val songsAdapter: SongsListAdapter): ViewType()

}
