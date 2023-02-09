package com.example.musicapp.view.fragments.classic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.databinding.FragmentSongsListBinding
import com.example.musicapp.utils.BaseFragment
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.UIState
import com.example.musicapp.view.adapters.ArtistsSongsAdapter

class ClassicFragment: BaseFragment() {

    private val binding by lazy {
        FragmentSongsListBinding.inflate(layoutInflater)
    }

    private val songsAdapter by lazy {
        ArtistsSongsAdapter{

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvSongsList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = songsAdapter
        }

        musicViewModel.classicMusic.observe(viewLifecycleOwner){
            when(it){
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    songsAdapter.updateArtistsSongsList(it.response.songItems)
                }
                is UIState.ERROR -> {
                    showError(it.error.localizedMessage){
                        musicViewModel.getSongs(Genres.CLASSIC)
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}