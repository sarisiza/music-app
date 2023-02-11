package com.example.musicapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentDisconnectedBinding
import com.example.musicapp.utils.BaseFragment
import com.example.musicapp.utils.Genres

class DisconnectedFragment : BaseFragment() {

    //binding to the UI
    private val binding by lazy {
        FragmentDisconnectedBinding.inflate(layoutInflater)
    }

    //variable to refresh the layout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    /**
     * Override onCreateView
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        swipeRefreshLayout = binding.swipeContainer

        //swipe-up to refresh
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            if(checkForInternet()){ //check internet connection
                //go to rock fragment
                findNavController().navigate(R.id.action_disconnect_fragment_to_rock_list)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}