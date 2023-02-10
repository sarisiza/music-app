package com.example.musicapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentDetailsBinding
import com.example.musicapp.model.domain.Song
import com.example.musicapp.utils.BaseFragment
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.IncorrectQuery
import com.squareup.picasso.Picasso


class DetailsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val currentSong: Song = musicViewModel.itemSelected.value?:Song()

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

        // Inflate the layout for this fragment
        return binding.root
    }

}