package com.example.musicapp.view.fragments.classic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapp.databinding.FragmentSongsListBinding
import com.example.musicapp.utils.BaseFragment

class ClassicFragment: BaseFragment() {

    private val binding by lazy {
        FragmentSongsListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

}