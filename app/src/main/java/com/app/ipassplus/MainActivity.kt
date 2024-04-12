package com.app.ipassplus
import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
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
import com.google.gson.Gson
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.core.IPassSDK
import com.sdk.ipassplussdk.model.request.aml_manual.AmlManualRequest
import com.sdk.ipassplussdk.model.request.facesimilarity.FaceSimilarityRequest
import com.sdk.ipassplussdk.model.request.livenesscheck.LivenessCheckRequest
import com.sdk.ipassplussdk.model.request.login.LoginRequest
import com.sdk.ipassplussdk.model.request.session_create.SessionCreateRequest
import com.sdk.ipassplussdk.model.request.valid_api.Image
import com.sdk.ipassplussdk.model.request.valid_api.ValidApiRequest
import com.sdk.ipassplussdk.model.response.aml_manual.AmlManualResponse
import com.sdk.ipassplussdk.model.response.facesimilarity.Data
import com.sdk.ipassplussdk.model.response.facesimilarity.FaceSimilarityResponse
import com.sdk.ipassplussdk.model.response.livenesscheck.LivenessCheckResponse
import com.sdk.ipassplussdk.model.response.login.LoginResponse
import com.sdk.ipassplussdk.model.response.session_create.FaceSessionCreateResponse
import com.sdk.ipassplussdk.model.response.valid_api.ValidApiResponse
import com.sdk.ipassplussdk.utils.Constants

class MainActivity : AppCompatActivity() {

    private var isAnimationStarted: Boolean = false
    private var mTimerAnimator: ValueAnimator? = null
    private var loadingDialog: AlertDialog? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    var token: String = "eyJhbGciOiJIUzI1NiJ9.aXBhc3Ntb2JpbGVAeW9wbWFpbC5jb21pcGFzcyBpcGFzcw.y66dMZJUkzYrRZoczlkNum8unLc910zIuGUVaQW5lUI"
    var auth_token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjYxNTQyNmMzY2RmZDA4NDlmYTQ1NzcxIiwiZW1haWwiOiJpcGFzc21vYmlsZUB5b3BtYWlsLmNvbSIsImlhdCI6MTcxMjgzMzE3NCwiZXhwIjoxNzEyODM0OTc0fQ.7mmV3gcHd54YQ8uohfKwI_RJW0GzIYH_SFG--VGZ_Zg"

    val sid = "183"
    val email = "ipassmobile@yopmail.com"

    val img1 = ""
    val img2 = ""
    var imageBase64: String = ""
    val sourceImageBase64: String = ""

    private fun findNavController():NavController? {
        val navHostFragment = (this as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.fragment_container) as? NavHostFragment
        return navHostFragment?.navController
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        IPassSDK.initiliazeDatabase(this, object : ResultListener<LoginResponse> {
//            override fun onSuccess(response: LoginResponse?) {
//
//                val pp = response?.user?.token
//                print(response)
//                Log.e("call","Login Response:: "+response.toString())
//                Toast.makeText(this@MainActivity, "Login Response::  "+response.toString(), Toast.LENGTH_LONG).show()
//            }
//
//            override fun onError(exception: String) {
//                print(exception)
//                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
//            }
//        })
//

        IPassSDK.initiliazeDatabase(this) {
            Log.e("initiliazeDatabase", it)
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        IPassSDK.ConfigureAws(this)
//        IPassSDK.FaceDetector(this, "e74739a2-fb79-4ee4-9528-887f51b8f061", (binding.root as ViewGroup))
        initNavigation()
//      //  VerifyLicense.verifyLicense(this)
//
//        val window = this.window
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.statusBarColor = this.resources.getColor(R.color.white)

        binding.qqqq.setOnClickListener {
            callApi()
        }


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
                /*if (imageUris.size > 0) {
                    loadingDialog = showDialog("Processing image")
                    if (imageUris.size == 1) {
                        getBitmap(imageUris[0], 1920, 1080, this)?.let { bitmap ->
                            val recognizeConfig = RecognizeConfig.Builder(Scenario.SCENARIO_FULL_PROCESS).setBitmap(bitmap).build()
                            DocumentReader.Instance().recognize(recognizeConfig, completion)
                        }
                    } else {
                        val bitmaps = arrayOfNulls<Bitmap>(imageUris.size)
                        for (i in bitmaps.indices) {
                            bitmaps[i] = getBitmap(imageUris[i], 1920, 1080, this)
                        }
                        val recognizeConfig = RecognizeConfig.Builder(Scenario.SCENARIO_FULL_PROCESS).setBitmaps(bitmaps).build()
                        DocumentReader.Instance().recognize(recognizeConfig, completion)
                    }
                }*/
            }
        }
    }

    private fun hideDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
    override fun onPause() {
        super.onPause()
        hideDialog()
    }
    //** api**///

    @RequiresApi(Build.VERSION_CODES.O)
    fun callApi(){
//        getLoginRequest()
//       getLivenessCheckRequest()
//        getFaceSimilarityRequest()
       // checkFaceAnalysisRequest()
        sessionCreateRequest(this.binding.root as ViewGroup)
//        sessionResultRequest()
//        amlManualRequest()
//        uploadOcrDocuments()
//        validApiRequest()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLoginRequest() {

//        var email: String = "yazan12@gmail.com"
        var email: String = "ipassmobile@yopmail.com"
        var password: String = "Admin@123#"

        val request = LoginRequest(email,password)

//        IPassSDK.getLoginRequest(this, object : ResultListener<LoginResponse> {
//            override fun onSuccess(t: LoginResponse?) {
//
//                val pp = t?.user?.token
//                print(t)
//               Log.e("call","Login Response:: "+t.toString())
//               Toast.makeText(this@MainActivity, "Login Response::  "+t.toString(), Toast.LENGTH_LONG).show()
//            }
//
//            override fun onError(exception: String) {
//                print(exception)
//                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
//            }
//        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sessionCreateRequest(bindViewGroup: ViewGroup) {

        val token = Constants.TOKEN
        val request = SessionCreateRequest()
        request.email = email
        request.authToken = auth_token

        IPassSDK.faceSessionCreateRequest(this,bindViewGroup) {

        }


//            , object : ResultListener<FaceSessionCreateResponse> {
//            override fun onSuccess(response: FaceSessionCreateResponse?) {
//                print(response)
//               Log.e("call","Session Create Response:: "+response.toString())
//               Toast.makeText(this@MainActivity, "Session Create Response::  "+response.toString(), Toast.LENGTH_LONG).show()
//            }
//
//            override fun onError(exception: String) {
//                print(exception)
//                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
//            }
//        })
    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun sessionResultRequest() {
//
//        val token = Constants.TOKEN
//        val sessionId = "def4d0d8-32b9-48bd-ba1a-2e74ab672e84"
//        val sid = "12"
//        val email = "mrverma91378@gmail.com"
//        val request = SessionResultRequest()
//        request.authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjVmMmRiMGNhYTJkMmQ3ZTBmMDM3ZjNjIiwiZW1haWwiOiJ5YXphbjEyMzNAZ21haWwuY29tIiwiaWF0IjoxNzEyMjIzNjI5LCJleHAiOjE3MTIyMjU0Mjl9.8Sy8dchwLpjkPfrGM0SOG9VYhgQqgE3LEY9N4QawwHY"
//
//        IPassSDK.faceSessionResultRequest(this, token, sessionId, sid, email, request, object : ResultListener<BaseModel<SessionResultResponse>> {
//            override fun onSuccess(t: Any?) {
//                print(t)
//               Log.e("call","Session Result Response:: "+t.toString())
//               Toast.makeText(this@MainActivity, "Session Result Response::  "+t.toString(), Toast.LENGTH_LONG).show()
//            }
//
//            override fun onError(exception: String) {
//                Log.e("call", exception)
//                print(exception)
//                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
//            }
//        })
//    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getLivenessCheckRequest() {
        val request = LivenessCheckRequest()
//        request.imageBase64 = imageBase64
//        request.email = email
        request.auth_token = auth_token

        IPassSDK.getLivenessCheckRequest(this, token, request, object : ResultListener<LivenessCheckResponse> {
            override fun onSuccess(response: LivenessCheckResponse?) {
                print(response)
                Log.e("call","Livenesscheck Response:: "+response.toString())
                Toast.makeText(this@MainActivity, "Livenesscheck Response::  "+response.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onError(exception: String) {
                print(exception)
                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFaceSimilarityRequest() {
        val request = FaceSimilarityRequest()
        request.sid = sid
        request.email = email
        request.authToken = auth_token
        request.sourceImageBase64 = img1
        request.targetImageBase64 = img2
        IPassSDK.getFaceSimilarityRequest(this, Constants.TOKEN, request, object : ResultListener<Data> {
            override fun onSuccess(response: Data?) {
                print(response)
                Log.e("call","Face similarity Response:: "+response.toString())
                Toast.makeText(this@MainActivity, "Face similarity Response::  "+response.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onError(exception: String) {
                print(exception)
                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
            }
        })
    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun uploadOcrDocuments() {
//        val request = OcrPostdataRequest()
//        request.sid = sid
//        request.email = email
//        request.custEmail = "anshul12@gmail.com"
//        request.workflow = "10032"
//        request.authToken = auth_token
//        request.image1 = sourceImageBase64
////        request.image2 = sourceImageBase64
//        IPassSDK.uploadOcrDocuments(this, token, request, object : ResultListener<BaseModel<OcrPostDataResponse>> {
//            override fun onSuccess(t: Any?) {
//                print(t)
//                Log.e("call","Ocr data Response:: "+t.toString())
//                Toast.makeText(this@MainActivity, "Ocr data Response::  "+t.toString(), Toast.LENGTH_LONG).show()
//            }
//            override fun onError(exception: String) {
//                print(exception)
//                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun checkFaceAnalysisRequest() {
//        val request = CheckFaceAnalysisRequest(imageBase64)
//        IPassSDK.checkFaceAnalysisRequest(this,token, auth_token, request, object : ResultListener<BaseModel<CheckFaceAnalysisResponse>> {
//            override fun onSuccess(t: Any?) {
//                print(t)
//                Log.e("call","CheckFaceAnalysis Response:: "+t.toString())
//                val jsonString = Gson().toJson(t)
//// Log the JSON string
//                Log.d("JSON String::::::  ", jsonString)
//
//                Toast.makeText(this@MainActivity, "CheckFaceAnalysis Response::  "+t.toString(), Toast.LENGTH_LONG).show()
//            }
//            override fun onError(exception: String) {
//                print(exception)
//                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//
    @RequiresApi(Build.VERSION_CODES.O)
        fun amlManualRequest() {
        val request = AmlManualRequest()
        request.sid = sid
        request.email = email
        request.custEmail = "anuj12@gmail.com"
       request.entityName = "YAZAN MUNIR MOHAMMAD"
        request.fuzLevel = "30"
        request.authToken = auth_token

        IPassSDK.amlManualRequest(this, token, object : ResultListener<AmlManualResponse> {
            override fun onSuccess(response: AmlManualResponse?) {
                print(response)
                Log.e("call","Aml Manual Response:: "+response.toString())
                val jsonString = Gson().toJson(response)
//                Log the JSON string
                Log.d("JSON String::::::  ", jsonString)
                Toast.makeText(this@MainActivity, "Aml Manual Response::  "+response.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onError(exception: String) {
                Log.e("call","ERR:: "+exception.toString())
                print(exception)
                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
            }
        })
    }
//
    @RequiresApi(Build.VERSION_CODES.O)
    fun validApiRequest() {
        val request = ValidApiRequest()
        request.sid = sid
        request.email = email
        request.applicationId = "4845116"

        val image1 = Image()
        image1.image = img1
        image1.type = "FRONT"

        val image2 = Image()
        image2.image = img2
        image2.type = "BACK"

        request.image = arrayListOf(image1, image2)
        request.authToken = auth_token

        IPassSDK.validApiRequest(this, token, request, object : ResultListener<ValidApiResponse> {
            override fun onSuccess(response: ValidApiResponse?) {
                print(response)
                Log.e("call","Valid Api Response:: "+response.toString())
                val jsonString = Gson().toJson(response)
// Log the JSON string
                Log.d("JSON String::::::  ", jsonString)
                Toast.makeText(this@MainActivity, "Aml Manual Response::  "+response.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onError(exception: String) {
                print(exception)
                Toast.makeText(this@MainActivity, exception, Toast.LENGTH_LONG).show()
            }
        })
    }

}


