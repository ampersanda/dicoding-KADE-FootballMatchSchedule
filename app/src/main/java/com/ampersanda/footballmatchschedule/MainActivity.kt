package com.ampersanda.footballmatchschedule

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ampersanda.footballmatchschedule.fragments.FavouriteMatchFragment
import com.ampersanda.footballmatchschedule.fragments.LastMatchFragment
import com.ampersanda.footballmatchschedule.fragments.NextMatchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0f

        val bottomNav = findViewById<BottomNavigationView>(R.id.main_bottom_nav)

        bottomNav.setOnNavigationItemSelectedListener { it: MenuItem ->
            when (it.itemId) {
                R.id.main_navigation_last_match -> {
                    supportActionBar?.title = getString(R.string.main_navigation_last_match)
                    FragmentUtility.changeFragment(supportFragmentManager, LastMatchFragment())
                    true
                }

                R.id.main_navigation_next_match -> {
                    supportActionBar?.title = getString(R.string.main_navigation_next_match)
                    FragmentUtility.changeFragment(supportFragmentManager, NextMatchFragment())
                    true
                }

                R.id.main_navigation_fav -> {
                    supportActionBar?.title = getString(R.string.main_navigation_fav)
                    FragmentUtility.changeFragment(supportFragmentManager, FavouriteMatchFragment())
                    true
                }

                else -> false
            }
        }

        bottomNav.selectedItemId = R.id.main_navigation_next_match
    }
}
