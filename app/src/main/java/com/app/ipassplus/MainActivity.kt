package com.app.ipassplus
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.app.ipassplus.Utils.Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
import com.app.ipassplus.databinding.ActivityMainBinding
import com.sdk.ipassplussdk.core.IPassSDK

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private fun findNavController():NavController? {
        val navHostFragment = (this as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.fragment_container) as? NavHostFragment
        return navHostFragment?.navController
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IPassSDK.initializeDatabase(this) {
            Log.e("initializeDatabase", it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

    }
    @RequiresApi(Build.VERSION_CODES.O)
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
                        recognizeImage()
                    }
                    R.id.cameraFragment -> {
//                        IPassSDK.showScannerRequest(this)
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
//                        IPassSDK.showScannerRequest(this)
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


    fun recognizeImage() {
        val readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(this, readImagePermission)
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(readImagePermission),
                PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        } else
            createImageBrowsingRequest()
    }

    private fun createImageBrowsingRequest() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        imageBrowsingIntentLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }
    @SuppressLint("WrongConstant")
    @Transient
    val imageBrowsingIntentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.let { intent ->
                val imageUris = ArrayList<Uri>()
                if (intent.clipData == null) {
                    intent.data?.let { uri ->
                        imageUris.add(uri)
                    }
                } else {
                    intent.clipData?.let { clipData ->
                        for (i in 0 until clipData.itemCount) {
                            imageUris.add(clipData.getItemAt(i).uri)
                        }
                    }
                }
            }
        }
    }

}


