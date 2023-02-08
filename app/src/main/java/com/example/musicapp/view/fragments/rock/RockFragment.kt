package com.example.musicapp.view.fragments.rock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.musicapp.R

/**
 * A simple [Fragment] subclass.
 * Use the [RockFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RockFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rock, container, false)
    }

}