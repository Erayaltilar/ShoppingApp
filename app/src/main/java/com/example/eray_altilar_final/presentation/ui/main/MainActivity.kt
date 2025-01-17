package com.example.eray_altilar_final.presentation.ui.main

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.core.SharedPreferencesManager
import com.example.eray_altilar_final.databinding.ActivityMainBinding
import com.example.eray_altilar_final.databinding.DrawerHeaderBinding
import com.example.eray_altilar_final.presentation.ui.cart.CartFragment
import com.example.eray_altilar_final.presentation.ui.favorites.FavoritesFragment
import com.example.eray_altilar_final.presentation.ui.product.ProductsFragment
import com.example.eray_altilar_final.presentation.ui.profile.ProfileFragment
import com.example.eray_altilar_final.presentation.ui.search.SearchFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var headerBinding: DrawerHeaderBinding
    private val viewModel by viewModels<MainViewModel>()

    private var navContent: FrameLayout? = null

    private val navigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {
        binding.drawerLayout.close()
        when (it.itemId) {
            R.id.productsFragment -> {
                val fragment = ProductsFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.profileFragment -> {
                val fragment = ProfileFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.cartFragment -> {
                val fragment = CartFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.favoritesFragment -> {
                val fragment = FavoritesFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.searchFragment -> {
                val fragment = SearchFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharedPreferencesManager.init(this)

        navContent = binding.navHostFragment
        val navigation = binding.navigationView
        navigation.setNavigationItemSelectedListener(navigationItemSelectedListener)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val fragment = ProductsFragment()
        addFragment(fragment)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            0, 0
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val headerView = binding.navigationView.getHeaderView(0)
        headerBinding = DrawerHeaderBinding.bind(headerView)
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                headerBinding.textView.text ="${uiState.user?.firstName} ${uiState.user?.lastName}"
            }
        }
    }

    private fun addFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                com.google.android.material.R.anim.design_bottom_sheet_slide_in,
                com.google.android.material.R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.navHostFragment, fragment, fragment.javaClass.simpleName)
            .commit()
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}