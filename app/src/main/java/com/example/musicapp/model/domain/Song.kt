package com.example.musicapp.model.domain

import android.provider.MediaStore.Audio.Genres
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musicapp.model.SongItem
import java.lang.Math.random

/**
 * Represents a Song
 * This data class defines de database table
 */
@Entity
data class Song(
    @PrimaryKey
    val trackId: Int = 0,
    @ColumnInfo("song_name")
    val trackName: String = "",
    val artistName: String = "",
    @ColumnInfo("image_url")
    val artworkUrl60: String = "",
    @ColumnInfo("album_name")
    val collectionName: String = "",
    val previewUrl: String = "",
    val releaseDate: String = "",
    @ColumnInfo("song_price")
    val trackPrice: Double = 0.0,
    val genre: String = "rock"
)

/**
 * Maps the JSON response to the Song object
 */
fun SongItem?.mapToSong(genreName: String): Song =
    Song(
        trackId = this?.trackId ?: random().toInt(),
        trackName = this?.trackName ?: "",
        artistName = this?.artistName ?: "",
        artworkUrl60 = this?.artworkUrl60 ?: "",
        collectionName = this?.collectionName ?: "",
        previewUrl = this?.previewUrl ?: "",
        releaseDate = this?.releaseDate ?: "",
        trackPrice = this?.trackPrice ?: 0.0,
        genre = genreName
    )

