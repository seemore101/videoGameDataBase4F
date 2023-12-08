package com.example.videoGameDataBase.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.videoGameDataBase.R
import com.example.videoGameDataBase.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.NavHostFragment_Main_Activity) as NavHostFragment

        navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.NavBar_Activity_main_navbar)
            .setupWithNavController(navController)

        appBarConfiguration= AppBarConfiguration(setOf(R.id.listingFragment,R.id.wishlistFragment))

         //With Navigation Component
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id==R.id.detailsFragment){
                binding.NavBarActivityMainNavbar.visibility=View.GONE
            }else{
                binding.NavBarActivityMainNavbar.visibility=View.VISIBLE
            }
        }


        binding.NavBarActivityMainNavbar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navbar_games->{
                    navController.previousBackStackEntry?.let {
                        navController.navigateUp()
                        //onBackPressed()
                    }?:navController.navigate(R.id.listingFragment)
                    true
                }
                R.id.navbar_wishlist->{
                    navController.navigate(R.id.wishlistFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
        binding.NavBarActivityMainNavbar.setOnItemReselectedListener {
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


}