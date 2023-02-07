package com.example.musicapp.rest

import com.example.musicapp.model.MusicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET("search")
    suspend fun getSongs(
        @Query("term") genre: String
    ): Response<List<MusicResponse>>

    companion object{
        const val BASE_URL = "https://itunes.apple.com/"
    }

}