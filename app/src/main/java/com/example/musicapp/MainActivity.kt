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

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /**
     * Creating the binding for the activity_main layout
     */
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.frag_container) as NavHostFragment
        setupActionBarWithNavController(navHost.navController)
        binding.bnvMenu.setupWithNavController(navHost.navController)
    }

    /**
     * Support Navigation Up
     */

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.frag_container).navigateUp()
    }


}