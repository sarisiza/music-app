package com.example.musicapp.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentDetailsBinding
import com.example.musicapp.model.domain.Song
import com.example.musicapp.utils.BaseFragment
import com.squareup.picasso.Picasso


class DetailsFragment : BaseFragment() {

    //binding to the UI
    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    //variable to get current song
    private lateinit var currentSong: Song

    //instantiate exo player
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L


    /**
     * Overrides onCreateView
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //get current song
        currentSong = musicViewModel.itemSelected.value?:Song()

        //bind to UI
        binding.tvSongName.text = currentSong.trackName
        binding.tvAlbumName.text = currentSong.collectionName
        binding.tvArtistName.text = currentSong.artistName
        binding.tvReleaseDate.text = currentSong.releaseDate
        binding.tvSongPrice.text = currentSong.trackPrice.toString()

        //create a Picasso object to handle the image
        if(currentSong.artworkUrl60 != "") {
            Picasso
                .get()
                .load(currentSong.artworkUrl60)
                .centerCrop()
                .resize(700, 0)
                .placeholder(R.drawable.ic_image_search_24)
                .error(R.drawable.ic_broken_image_24)
                .into(binding.ivSongCover)
        }

        //initialize exo player
        initializePlayer()

        // Inflate the layout for this fragment
        return binding.root
    }

    /**
     * Override onStop method
     */
    override fun onStop() {
        super.onStop()
        //release exo-player
        releasePlayer()
    }

    /**
     * Method to release exo-player
     */
    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition //get current position for playback
            currentItem = exoPlayer.currentMediaItemIndex //get current item
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release() //release player
        }
        player = null //terminate player
    }

    /**
     * Initialize Exo player
     */
    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build() //build exo-player
            .also { exoPlayer ->
                binding.musicView.player = exoPlayer //bind player to UI
                val mediaItem: MediaItem = MediaItem.fromUri(currentSong.previewUrl)
                exoPlayer.setMediaItem(mediaItem) //get current song to player
                exoPlayer.playWhenReady = playWhenReady //play when ready
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }


}