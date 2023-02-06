package com.example.musicapp.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Response format of the API
 */
@JsonClass(generateAdapter = true)
data class MusicResponse(
    @Json(name = "resultCount")
    val resultCount: Int? = null,
    @Json(name = "results")
    val songItems: List<SongItem?>? = null
)