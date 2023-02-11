package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.view.fragments.classic.ClassicFragment
import com.example.musicapp.view.fragments.pop.PopFragment
import com.example.musicapp.view.fragments.rock.RockFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity if the app
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //binding to the UI
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    /**
     * Overriding onCreate method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set the UI
        setContentView(binding.root)

        //Add navigation
        val navHost = supportFragmentManager
            .findFragmentById(R.id.frag_container) as NavHostFragment
        //Navigation between fragments
        setupActionBarWithNavController(navHost.navController)
        //Setup navigation for bottom menu
        binding.bnvMenu.setupWithNavController(navHost.navController)
    }

    /**
     * Support Navigation Up
     */
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.frag_container).navigateUp()
    }

}