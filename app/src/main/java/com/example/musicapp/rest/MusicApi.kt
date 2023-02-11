package com.example.musicapp.rest

import com.example.musicapp.model.MusicResponse
import com.example.musicapp.utils.Genres
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API class for network connection
 */
interface MusicApi {

    /**
     * Defines the method to get the list of songs
     */
    @GET(SEARCH)
    suspend fun getSongsList(
        @Query("term") genre: String
    ): Response<MusicResponse>

    /**
     * Defines the API endpoint
     */
    companion object{
        const val BASE_URL = "https://itunes.apple.com/"
        const val SEARCH = "search"
    }

}