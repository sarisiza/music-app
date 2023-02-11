package com.example.musicapp.view.fragments.classic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.R
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
            musicViewModel.selectItem(it)
            findNavController().navigate(R.id.action_classic_list_to_song_details)
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
                    songsAdapter.updateArtistsSongsList(it.response)
                }
                is UIState.ERROR -> {
                    showError(it.error.localizedMessage){
                        musicViewModel.getSongs(Genres.CLASSIC)
                    }
                }
            }
        }

        binding.swipeContainer.setOnRefreshListener {
            musicViewModel.getSongs(Genres.CLASSIC)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if(!musicViewModel.fragmentState){
            musicViewModel.getSongs(Genres.CLASSIC)
        }
    }

}