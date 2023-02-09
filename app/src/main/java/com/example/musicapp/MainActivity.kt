package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
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

    }

    override fun onResume() {
        super.onResume()
        binding.bnvMenu.setOnItemSelectedListener {
            handleBottomNavigation(
                it.itemId
            )
        }
    }

    private fun handleBottomNavigation(menuItemId: Int): Boolean =
        when(menuItemId){
            R.id.menu_rock -> {
                swapFragments(RockFragment())
                true
            }
            R.id.menu_classic -> {
                swapFragments(ClassicFragment())
                true
            }
            R.id.menu_pop -> {
                swapFragments(PopFragment())
                true
            }
            else -> {false}
        }

    private fun swapFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container, fragment)
            .commit()
    }

    /**
     * Support Navigation Up
     */

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.frag_container).navigateUp()
    }


}