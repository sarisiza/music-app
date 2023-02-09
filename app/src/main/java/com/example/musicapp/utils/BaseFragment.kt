package com.example.musicapp.utils

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicapp.viewmodel.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment: Fragment() {


    protected val musicViewModel: MusicViewModel by lazy {
        ViewModelProvider(requireActivity())[MusicViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        if(!musicViewModel.fragmentState){
            musicViewModel.getSongs(Genres.ROCK)
        }
    }

    protected fun showError(message: String, action: () -> Unit){
        AlertDialog.Builder(requireActivity())
            .setTitle("Error Occurred")
            .setMessage(message)
            .setPositiveButton("RETRY"){dialog, _ ->
                action()
                dialog.dismiss()
            }
            .setNegativeButton("DISMISS"){dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    /**
     * Method to check orientation change
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        musicViewModel.fragmentState = true
    }

    /**
     * Method to check orientation back
     */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        musicViewModel.fragmentState = false
    }

}