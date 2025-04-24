package com.example.verdigo.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.verdigo.R
import com.example.verdigo.presentation.fragments.CategoriesFragment
import com.example.verdigo.presentation.fragments.FavoritesFragment
import com.example.verdigo.presentation.fragments.HomeFragment
import com.example.verdigo.presentation.fragments.MapsFragment
import com.example.verdigo.presentation.fragments.ProductsFragment
import com.example.verdigo.presentation.fragments.ProfileFragment
import com.example.verdigo.presentation.fragments.SearchFragment
import com.example.verdigo.presentation.fragments.StartFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        navView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_home -> StartFragment()
                R.id.nav_favorites -> FavoritesFragment()
                R.id.nav_search -> SearchFragment()
                R.id.nav_maps -> MapsFragment()
                R.id.nav_perfil -> ProfileFragment()
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
                true
            } ?: false
        }


        // Set default selection
        navView.selectedItemId = R.id.nav_home

        val logoutIcon: ImageView = findViewById(R.id.logoutIcon)
        logoutIcon.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

}