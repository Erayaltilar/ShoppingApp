package com.example.eray_altilar_final.presentation.ui.main

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.eray_altilar_final.R
import com.example.eray_altilar_final.databinding.ActivityMainBinding
import com.example.eray_altilar_final.databinding.DrawerHeaderBinding
import com.example.eray_altilar_final.presentation.ui.cart.CartFragment
import com.example.eray_altilar_final.presentation.ui.product.ProductsFragment
import com.example.eray_altilar_final.presentation.ui.profile.ProfileFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var headerBinding: DrawerHeaderBinding

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
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        headerBinding.apply {
            textView.text = "Eray Altilar"
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