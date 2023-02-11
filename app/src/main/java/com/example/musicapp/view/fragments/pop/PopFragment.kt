package com.example.musicapp.view.fragments.pop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentSongsListBinding
import com.example.musicapp.utils.BaseFragment
import com.example.musicapp.utils.Genres
import com.example.musicapp.utils.UIState
import com.example.musicapp.view.adapters.ArtistsSongsAdapter

/**
 * Fragment that shows the list of pop songs
 */
class PopFragment: BaseFragment() {

    //binding to the UI
    private val binding by lazy {
        FragmentSongsListBinding.inflate(layoutInflater)
    }

    /**
     * Creating the adapter for the RecyclerView
     */
    private val songsAdapter by lazy {
        ArtistsSongsAdapter{
            musicViewModel.selectItem(it) //save clicked song
            //go to details fragment
            findNavController().navigate(R.id.action_pop_list_to_song_details)
        }
    }

    //variable to refresh the layout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    /**
     * Overriding onStart method
     * Resets the fragment state
     */
    override fun onStart() {
        super.onStart()
        musicViewModel.updateFragmentState(false)
    }

    /**
     * Overriding onCreateView method
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        swipeRefreshLayout = binding.swipeContainer

        //binding the RecyclerView
        //Adding a LinearLayoutManager and the adapter
        binding.rvSongsList.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = songsAdapter
        }

        //updates the list of songs
        updateList()

        //swipe-up to refresh
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            musicViewModel.getSongs(Genres.POP)
            updateList()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    /**
     * Overriding onResume method
     */
    override fun onResume() {
        super.onResume()
        musicViewModel.fragmentState.observe(viewLifecycleOwner) {
            if (it == false) { //check the fragment state
                if (!musicViewModel.songListIsEmpty(Genres.POP)) { //checks if database has data
                    musicViewModel.getSongs(Genres.POP) //get songs from database
                } else if (checkForInternet()) { //checks internet connectivity
                    musicViewModel.getSongs(Genres.POP) //get songs from API
                    musicViewModel.updateSongsDatabaseByGenre(Genres.POP) //update database
                } else {
                    //go to disconnection fragment
                    findNavController().navigate(R.id.action_pop_list_to_disconnect_fragment)
                }
            }
        }
    }

    /**
     * Method to update the songs list
     */
    private fun updateList(){
        musicViewModel.popMusic.observe(viewLifecycleOwner) {//observe the livedata
            when (it) {
                is UIState.LOADING -> {} //do nothing on loading state
                is UIState.SUCCESS -> {
                    songsAdapter.updateArtistsSongsList(it.response) //update lists on success
                }
                is UIState.ERROR -> { //handle error case
                    showError(it.error.localizedMessage) {
                        if(checkForInternet() || !musicViewModel.songListIsEmpty(Genres.POP)) {
                            musicViewModel.getSongs(Genres.POP) //retry call
                        } else{
                            //go to disconnect fragment
                            findNavController().navigate(R.id.action_pop_list_to_disconnect_fragment)
                        }
                    }
                }
            }
        }
    }

}