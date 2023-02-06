package com.example.musicapp.rest

import com.example.musicapp.model.MusicResponse
import com.example.musicapp.model.SongItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET()
    suspend fun getSongs(
        @Query("term") genre: String
    ): Response<MusicResponse>

    //https://itunes.apple.com/search?term=rock
    companion object{
        const val BASE_URL = "https://itunes.apple.com/search"
    }

}