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

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private var player: ExoPlayer? = null
    private lateinit var currentSong: Song

    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        currentSong = musicViewModel.itemSelected.value?:Song()

        binding.tvSongName.text = currentSong.trackName
        binding.tvAlbumName.text = currentSong.collectionName
        binding.tvArtistName.text = currentSong.artistName
        binding.tvReleaseDate.text = currentSong.releaseDate
        binding.tvSongPrice.text = currentSong.trackPrice.toString()

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

        initializePlayer()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }


    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.musicView.player = exoPlayer
                val mediaItem: MediaItem = MediaItem.fromUri(currentSong.previewUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()
            }
    }


}