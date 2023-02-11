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

    private val binding by lazy {
        FragmentDisconnectedBinding.inflate(layoutInflater)
    }

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        swipeRefreshLayout = binding.swipeContainer

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            if(checkForInternet()){
                findNavController().navigate(R.id.action_disconnect_fragment_to_rock_list)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}