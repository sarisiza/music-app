package com.example.musicapp.model.domain

import com.example.musicapp.model.SongItem

data class Song(
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    val artworkUrl60: String,
    val collectionName: String,
    val previewUrl: String,
    val releaseDate: String,
    val trackPrice: Double
)

fun SongItem?.mapToSong(): Song =
    Song(
        trackId = this?.trackId ?: 0,
        trackName = this?.trackName ?: "",
        artistName = this?.artistName ?: "",
        artworkUrl60 = this?.artworkUrl60 ?: "",
        collectionName = this?.collectionName ?: "",
        previewUrl = this?.previewUrl ?: "",
        releaseDate = this?.releaseDate ?: "",
        trackPrice = this?.trackPrice ?: 0.0
    )

