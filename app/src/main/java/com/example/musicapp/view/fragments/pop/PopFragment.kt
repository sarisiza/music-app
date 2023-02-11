package com.example.musicapp.view.fragments.pop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentSongsListBinding
import com.example.musicapp.utils.BaseFragment
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.UIState
import com.example.musicapp.view.adapters.ArtistsSongsAdapter

class PopFragment: BaseFragment() {

    private val binding by lazy {
        FragmentSongsListBinding.inflate(layoutInflater)
    }

    private val songsAdapter by lazy {
        ArtistsSongsAdapter{
            musicViewModel.selectItem(it)
            findNavController().navigate(R.id.action_pop_list_to_song_details)
        }
    }

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onStart() {
        super.onStart()
        musicViewModel.updateFragmentState(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        swipeRefreshLayout = binding.swipeContainer

        binding.rvSongsList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = songsAdapter
        }

        updateList()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            musicViewModel.getSongs(Genres.POP)
            updateList()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        musicViewModel.fragmentState.observe(viewLifecycleOwner) {
            if (it == false) {
                if (!musicViewModel.songListIsEmpty(Genres.POP)) {
                    musicViewModel.getSongs(Genres.POP)
                } else if (checkForInternet()) {
                    musicViewModel.getSongs(Genres.POP)
                    musicViewModel.updateSongsDatabaseById(Genres.POP)
                } else {
                    findNavController().navigate(R.id.action_pop_list_to_disconnect_fragment)
                }
            }
        }
    }

    private fun updateList(){
        if(checkForInternet() || !musicViewModel.songListIsEmpty(Genres.POP)) {
            musicViewModel.popMusic.observe(viewLifecycleOwner) {
                when (it) {
                    is UIState.LOADING -> {}
                    is UIState.SUCCESS -> {
                        songsAdapter.updateArtistsSongsList(it.response)
                    }
                    is UIState.ERROR -> {
                        showError(it.error.localizedMessage) {
                            musicViewModel.getSongs(Genres.POP)
                        }
                    }
                }
            }
        } else{
            findNavController().navigate(R.id.action_pop_list_to_disconnect_fragment)
        }
    }

}