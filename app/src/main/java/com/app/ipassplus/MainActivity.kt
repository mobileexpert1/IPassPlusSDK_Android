package com.app.ipassplus
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.app.ipassplus.Utils.Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
import com.app.ipassplus.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.core.IPassSDK
import com.sdk.ipassplussdk.model.response.login.LoginResponse
import com.sdk.ipassplussdk.resultCallbacks.InitializeDatabaseCompletion

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var progressDialog: AlertDialog
    private lateinit var splitInstallManager: SplitInstallManager
    private val coreModule = "document_reader_sdk"
    private val email = "mabusanimeh@access2arabia.com"
    private val password = "A2a@123456"
    private val apptoken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjYzODg4MWYyYzNmMDFmMTg5OTNlMWI4IiwiZW1haWwiOiJtYWJ1c2FuaW1laEBhY2Nlc3MyYXJhYmlhLmNvbSIsImlhdCI6MTcxNTE2MDU1MywiZXhwIjoxNzE1MTYyMzUzfQ.Jyp8s_c3oc2grx2_Xip8yMTIU3_TZCctbEXnsyAMKLw"


    companion object {
        var authToken = ""
    }

    private fun findNavController():NavController? {
        val navHostFragment = (this as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.fragment_container) as? NavHostFragment
        return navHostFragment?.navController
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splitInstallManager = SplitInstallManagerFactory.create(this)
        loadModule(coreModule)

//        progressDialog = showProgressDialog(this@MainActivity, "Initializing")
        IPassSDK.initializeDatabase(this, object: InitializeDatabaseCompletion {
            override fun onProgressChanged(progress: Int) {
//                progressDialog.setTitle("Downloading database $progress%")
            }

            override fun onCompleted(
                status: Boolean,
                message: String?
            ) {
//                progressDialog.hide()
                Log.e("onCompleted", message!!)
//               Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                if (status) getToken()
            }

        })

        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
    }

    private fun loadModule(name: String) {
        val request = SplitInstallRequest.newBuilder()
            .addModule(name)
            .build()

        splitInstallManager.startInstall(request)
            .addOnFailureListener {
                Log.e("splitInstallManager", it.message!!)
            }
    }
    
        fun showProgressDialog(context: Context, msg: String): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(context, R.style.Theme_MyApp_Dialog_Alert)
        dialog.background = ResourcesCompat.getDrawable(context.resources, R.drawable.rounded, context.theme)
        dialog.setTitle(msg)

        // Inflate a custom layout for the dialog
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.simple_dialog, null)
        dialog.setView(dialogView)

        dialog.setCancelable(false)

        return dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getToken() {
//        progressDialog.setTitle("Generating Token Please wait!")
//        progressDialog.show()
        IPassSDK.getAuthToken(this@MainActivity, email, password, object : ResultListener<LoginResponse> {
            override fun onSuccess(response: LoginResponse?) {
//                progressDialog.cancel()
                val authToken = response?.user?.token!!
                Companion.authToken = authToken
//                Toast.makeText(this@MainActivity, "Token Generated Successfully", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: String) {
//                progressDialog.cancel()
                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_SHORT).show()
            }
        })
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


