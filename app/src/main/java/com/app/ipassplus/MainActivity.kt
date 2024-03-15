package com.app.ipassplus
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.app.ipassplus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private fun findNavController():NavController? {
        val navHostFragment = (this as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.fragment_container) as? NavHostFragment
        return navHostFragment?.navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
      //  VerifyLicense.verifyLicense(this)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.white)

    }
    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navController
        )

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.label?.equals("DashboardFragment")!!) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
            else binding.bottomNavigationView.visibility = View.GONE
        }

            binding.bottomNavigationView.setOnNavigationItemReselectedListener {
                when(it.itemId) {
                    R.id.dashboardFragment -> {

                    }
                    R.id.cameraFragment -> {
                    }
                    R.id.menuFragment -> {

                    }
                }
            }

            binding.bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.dashboardFragment -> {
                        findNavController()?.navigate(R.id.dashboardFragment)
                        return@setOnItemSelectedListener true
                    }
                    R.id.cameraFragment -> {
                        return@setOnItemSelectedListener true
                    }
                    R.id.menuFragment -> {
                        findNavController()?.navigate(R.id.menuFragment)
                        return@setOnItemSelectedListener true
                    }
                    else -> {return@setOnItemSelectedListener false}
                }
            }
    }

}


