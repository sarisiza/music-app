package com.example.musicapp.utils

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.musicapp.R
import com.example.musicapp.view.adapters.ArtistsSongsAdapter
import com.example.musicapp.viewmodel.MusicViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Base fragment that inherits
 * common configurations to each of the fragments
 */
private const val TAG = "BaseFragment"
@AndroidEntryPoint
open class BaseFragment: Fragment() {

    //initiate the ViewModel
    protected val musicViewModel: MusicViewModel by lazy {
        ViewModelProvider(requireActivity())[MusicViewModel::class.java]
    }

    /**
     * Generates an error dialog
     */
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
     * Method to verify if there is a change in the configuration
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //update fragment state in case of configuration change
        musicViewModel.updateFragmentState(true)
    }

    /**
     * Method to verify if there is Network connection
     */
    protected fun checkForInternet():Boolean{
        //instantiate a connectivity manager
        val connectivityManager = requireContext().getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //instantiate the active network
        val network = connectivityManager.activeNetwork ?: return false
        //verify which network is active
        val activeNetwork = connectivityManager
            .getNetworkCapabilities(network) ?: return false
        return when{ //checks if the active network has transport
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true //wifi
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true //cellphone
            else -> false
        }

    }

}