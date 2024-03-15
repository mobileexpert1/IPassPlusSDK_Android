package com.ipassplus.fragments

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ipassplus.R
import com.ipassplus.activity.MainActivity
import com.ipassplus.activity.MainActivity.Companion.isFaceMatchingEnabled
import com.ipassplus.activity.MainActivity.Companion.sessionId
import com.ipassplus.activity.MainActivity.Companion.typeOfData
import com.ipassplus.activity.SettingsActivity
import com.ipassplus.databinding.FragmentDashboardTempBinding
import com.ipassplus.fragments.MainScreenViewpagerFragment.Companion.results
import com.ipassplus.models.modelData.request.SessionRequest
import com.ipassplus.models.response.SessionLiveness
import com.ipassplus.retrofit.ApiClient
import com.ipassplus.utils.Constants
import com.ipassplus.utils.Helpers
import com.ipassplus.utils.SharedPref
import com.regula.documentreader.api.DocumentReader
import com.regula.documentreader.api.completions.IDocumentReaderCompletion
import com.regula.documentreader.api.completions.IDocumentReaderPrepareCompletion
import com.regula.documentreader.api.completions.rfid.IRfidReaderCompletion
import com.regula.documentreader.api.config.ScannerConfig
import com.regula.documentreader.api.enums.CameraMode
import com.regula.documentreader.api.enums.CaptureMode
import com.regula.documentreader.api.enums.DocReaderAction
import com.regula.documentreader.api.enums.Scenario
import com.regula.documentreader.api.enums.eImageQualityCheckType
import com.regula.documentreader.api.enums.eRFID_AccessControl_ProcedureType
import com.regula.documentreader.api.enums.eRFID_AuthenticationProcedureType
import com.regula.documentreader.api.enums.eRFID_DataFile_Type
import com.regula.documentreader.api.enums.eRFID_NotificationCodes
import com.regula.documentreader.api.enums.eRFID_Password_Type
import com.regula.documentreader.api.enums.eRFID_SDK_ProfilerType
import com.regula.documentreader.api.errors.DocReaderRfidException
import com.regula.documentreader.api.errors.DocumentReaderException
import com.regula.documentreader.api.internal.parser.DocReaderResultsJsonParser
import com.regula.documentreader.api.params.DocReaderConfig
import com.regula.documentreader.api.results.DocumentReaderNotification
import com.regula.documentreader.api.results.DocumentReaderResults
import com.regula.documentreader.util.LicenseUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardTempFragment : Fragment(), View.OnClickListener {
    private val binding by lazy { FragmentDashboardTempBinding.inflate(layoutInflater) }
    private var mTimerAnimator: ValueAnimator? = null
    private var isAnimationStarted: Boolean = false
    private var currentScenario: String = ""
    @Transient
    private var loadingDialog: AlertDialog? = null
    @Transient
    private var initDialog: AlertDialog? = null

    private lateinit var pref: SharedPref


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SharedPref(requireContext())
        setProcessingPreferences()
        binding.rlFullProcessing.setOnClickListener(this)
        binding.rlBankCard.setOnClickListener(this)
        binding.MrzCD.setOnClickListener{
            isFaceMatchingEnabled = false
//            getSessionId()
            scanMrz()
        }
        binding.BardcodeCD.setOnClickListener{

            isFaceMatchingEnabled = false
//            getSessionId()
            scanBarcode()
        }
        binding.visualCD.setOnClickListener{

            isFaceMatchingEnabled = false
//            getSessionId()
            scanOCR()
        }

//        getSessionId()

        /*if (LicenseUtil.readFileFromAssets("Regula", "regula.license", requireContext()) == null
            && !MainActivity.isInitializedByBleDevice
        )
            Helpers.opaqueStatusBar(binding.root)
        if (DocumentReader.Instance().isReady)
            onInitComplete()*/

    }

    override fun onResume() {
        super.onResume()
        /*if (DocumentReader.Instance().isReady)
            return

        val license = LicenseUtil.readFileFromAssets(
            "Regula",
            "regula.license",
            requireContext()
        )

        license?.let {
            initDialog = showDialog("Initializing")
            getPrepareCompletion(it)
        }?.let { DocumentReader.Instance().prepareDatabase(requireContext(), "Full", it) }*/

        DocumentReader.Instance().customization().edit().setCameraFrameDefaultColor("#663398").apply()
    }

    private fun onInitComplete() {

        val path = DocumentReader.Instance().processParams().sessionLogFolder
        Log.e("Regula#####" , "Path: $path")

        val scenarios: Array<String?> = arrayOfNulls(DocumentReader.Instance().availableScenarios.size)
        for ((i, scenario) in DocumentReader.Instance().availableScenarios.withIndex())
            scenarios[i] = scenario.name
        if (scenarios.isNotEmpty()) {
            if (currentScenario.isEmpty()) {
                currentScenario = scenarios[0] ?: ""
            }
//            binding.scenarioPicker.visibility = View.VISIBLE
//            binding.scenarioPicker.maxValue = scenarios.size - 1
//            binding.scenarioPicker.wrapSelectorWheel = false
//            binding.scenarioPicker.displayedValues = scenarios
//            binding.scenarioPicker.value = scenarios.indexOf(currentScenario)
//            binding.scenarioPicker.setOnValueChangedListener { _, _, newVal ->
//                binding.scenarioPicker.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
//                currentScenario = scenarios[newVal] ?: ""
//            }
//            binding.recyclerView.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                requireContext(),
                "Available scenarios list is empty",
                Toast.LENGTH_SHORT
            ).show()
        }

        DocumentReader.Instance().setLocalizationCallback { stringId ->
            if(stringId == "strLookingDocument")
                return@setLocalizationCallback SettingsActivity.customString
            return@setLocalizationCallback null
        }
    }

    private fun getPrepareCompletion(license: ByteArray) =
        object : IDocumentReaderPrepareCompletion {
            override fun onPrepareProgressChanged(progress: Int) {
                initDialog!!.setTitle("Downloading database: $progress%")
            }

            override fun onPrepareCompleted(status: Boolean, error: DocumentReaderException?) {
                DocumentReader.Instance()
                    .initializeReader(requireContext(), DocReaderConfig(license))
                    { success, error_initializeReader ->
                        if (initDialog!!.isShowing)
                            initDialog!!.dismiss()
                        DocumentReader.Instance().customization().edit().setShowHelpAnimation(false).apply()

                        if (success) onInitComplete()
                        else
                            Toast.makeText(
                                requireContext(),
                                "Init failed:$error_initializeReader",
                                Toast.LENGTH_LONG
                            ).show()

                        Log.e("tag","error..."+error_initializeReader)
                    }
            }
        }


    private val completion = IDocumentReaderCompletion { action, results, error ->
        if(!isAnimationStarted) {
            mTimerAnimator?.let {
                it.start()
                isAnimationStarted = true
            }
        }
        if (action == DocReaderAction.COMPLETE
            || action == DocReaderAction.TIMEOUT) {
            hideDialog()
            cancelAnimation()

            val path = DocumentReader.Instance().processParams().sessionLogFolder
            Log.d("Regula" , "Path: $path")

            MainScreenViewpagerFragment.results = results!!

            if (pref.getBoolean(Constants.SOUND)) {
                var mediaPlayer = MediaPlayer.create(context, R.raw.success_sound)
                mediaPlayer.start()
            }
            if (pref.getBoolean(Constants.VIBRATION)) {
                val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(200)
                }
            }
            if (imageValidation(results)) {
                if (isFaceMatchingEnabled && pref.getBoolean(Constants.FACE_MATCHING)) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(R.id.selfieTimeFragment)
                    }, 1000)
                } else {
                    MainScreenViewpagerFragment.results = results!!
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(R.id.mainScreenViewpagerFragment)
                    }, 1000)
                }
            }

            if (DocumentReader.Instance().functionality().isManualMultipageMode) {
                if (results?.morePagesAvailable != 0) {
                    DocumentReader.Instance().startNewPage()
                    Handler(Looper.getMainLooper()).postDelayed({
                        showScanner()
                    }, 100)
                    return@IDocumentReaderCompletion
                } else {
                    DocumentReader.Instance().functionality().edit().setManualMultipageMode(false).apply()
                }
            }
            if (SettingsActivity.isRfidEnabled && results?.chipPage != 0) {
                DocumentReader.Instance().startRFIDReader(requireContext(), object: IRfidReaderCompletion() {
                    override fun onChipDetected() {
                        Log.d("Rfid", "Chip detected")
                    }

                    override fun onProgress(notification: DocumentReaderNotification) {
                        rfidProgress(notification.code, notification.value)
                    }

                    override fun onRetryReadChip(exception: DocReaderRfidException) {
                        Log.d("Rfid", "Retry with error: " + exception.errorCode)
                    }

                    override fun onCompleted(
                        rfidAction: Int,
                        results_RFIDReader: DocumentReaderResults?,
                        error: DocumentReaderException?
                    ) {
                        if (rfidAction == DocReaderAction.COMPLETE || rfidAction == DocReaderAction.CANCEL)
                            MainScreenViewpagerFragment.results = results_RFIDReader!!
//
                    }
                })
            } else
                MainScreenViewpagerFragment.results = results!!
        } else
            if (action == DocReaderAction.CANCEL) {
                if (DocumentReader.Instance().functionality().isManualMultipageMode)
                    DocumentReader.Instance().functionality().edit().setManualMultipageMode(false).apply()

                Toast.makeText(requireContext(), "Scanning was cancelled", Toast.LENGTH_LONG).show()
                hideDialog()
                cancelAnimation()
            }
            else if (action == DocReaderAction.ERROR) {
                Toast.makeText(requireContext(), "Error:$error", Toast.LENGTH_LONG).show()
                hideDialog()
                cancelAnimation()
            }
    }

    private fun getSessionId() {
        val sessionRequest = SessionRequest()
        sessionRequest.S3Bucket = "facelivenessimage"
        sessionRequest.S3KeyPrefix = "livenessimages"
        sessionRequest.accessKeyId = "AKIATYNYEEOCH5HZ5X2E"
        sessionRequest.secretAccessKey = "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"

        val call = ApiClient.apiService.getSessionId(sessionRequest)

        call.enqueue(object : Callback<SessionLiveness> {
            override fun onResponse(call: Call<SessionLiveness>, response: Response<SessionLiveness>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    Log.e("!!!!!!!!!!!", data?.sessionId!!)
                    sessionId = data.sessionId!!
                } else {
                    Log.e("!!!!!!!!!!!", response.message())
                    isFaceMatchingEnabled = false
                }
            }
            override fun onFailure(call: Call<SessionLiveness>, t: Throwable) {
                isFaceMatchingEnabled = false
                Log.e("!!!!!!!!!!!", t.message.toString())
            }
        })
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    private fun imageValidation(documentReaderResults: DocumentReaderResults): Boolean {

        if(documentReaderResults.status.detailsOptical.docType == 0) {
            Toast.makeText(requireContext(), "Please use valid document", Toast.LENGTH_SHORT).show()
//            Alert.shared.displayMyAlertMessage(title: "Alert", message: "Please use valid document", actionTitle: "Ok") {
//                print("doesn't match document")
//            }
            return true
        }

        if(documentReaderResults.status.detailsOptical.docType == 0 && documentReaderResults.status.detailsOptical.text == 0) {
            Toast.makeText(requireContext(), "Captured Image is blur. Plase try again", Toast.LENGTH_SHORT).show()
//            Alert.shared.displayMyAlertMessage(title: "Alert", message: "Captured Image is blur. Plase try again", actionTitle: "Ok") {
//                print("doesn't match document")
//            }
            return true
        }
        if(documentReaderResults.status.detailsOptical.docType == 0 && documentReaderResults.status.detailsOptical.text == 1) {
            Toast.makeText(requireContext(), "Document front and backside is mismatched", Toast.LENGTH_SHORT).show()
//            Alert.shared.displayMyAlertMessage(title: "Alert", message: "Document front and backside is mismatched", actionTitle: "Ok") {
//                print("doesn't match document")
//            }
            return true
        }
     /*   if(documentReaderResults.status.detailsOptical.docType == 1 && documentReaderResults.status.detailsOptical.text == 0) {
            Toast.makeText(requireContext(), "Captured Image is blur. Plase try again", Toast.LENGTH_SHORT).show()
//            Alert.shared.displayMyAlertMessage(title: "Alert", message: "Captured Image is blur. Plase try again", actionTitle: "Ok") {
//                print("doesn't match document")
//            }
            return true
        }*/
        if(documentReaderResults.status.detailsOptical.imageQA == 2) {
            Toast.makeText(requireContext(), "Image resolution is below threshold", Toast.LENGTH_SHORT).show()
//            Alert.shared.displayMyAlertMessage(title: "Alert", message: "Image resolution is below threshold", actionTitle: "Ok") {
//                print("doesn't match document")
//            }
            return true
        }

        if(documentReaderResults.status.detailsOptical.imageQA == 5) {
            Toast.makeText(requireContext(), "Document is not fully present in the image", Toast.LENGTH_SHORT).show()
//            Alert.shared.displayMyAlertMessage(title: "Alert", message: "Document is not fully present in the image", actionTitle: "Ok") {
//                print("doesn't match document")
//            }
            return true
        }

        if(documentReaderResults.status.detailsOptical.imageQA == 7) {
            Toast.makeText(requireContext(), "Portrait is not present", Toast.LENGTH_SHORT).show()
//            Alert.shared.displayMyAlertMessage(title: "Alert", message: "Portrait is not present", actionTitle: "Ok") {
//                print("doesn't match document")
//            }
            return true
        }
Log.e("comparison@@",documentReaderResults.textResult?.comparisonStatus.toString())


//        if(UserLocalStore.shared.clickedIndex == 0) {
//            if(UserLocalStore.shared.MultipageProcessing == true) {
                if(documentReaderResults.textResult?.comparisonStatus == 0) {
                    // show alert Please make sure front and back imgae is of same document
                    Toast.makeText(requireContext(), "Please make sure front and back imgae should be same documents", Toast.LENGTH_SHORT).show()
//                    Alert.shared.displayMyAlertMessage(title: "Alert", message: " Please make sure front and back imgae should be same documents ", actionTitle: "Ok") {
//                        print("doesn't match document")
//                    }
                    return true
                }
//            }
//        }



        if(documentReaderResults.textResult?.validityStatus == 0) {
            // show alert Image is blurry. Please try again
            Toast.makeText(requireContext(), "Captured Image is blur. Plase try again", Toast.LENGTH_SHORT).show()
//            Alert.shared.displayMyAlertMessage(title: "Alert", message: "Captured Image is blur. Plase try again", actionTitle: "Ok") {
//                print("doesn't match document")
//            }
            return true
        }



        Log.e("detailsOptical", "DocType is ${documentReaderResults.status.detailsOptical.docType}")
        Log.e("detailsOptical", "Text is ${documentReaderResults.status.detailsOptical.text}")
        if (documentReaderResults.status.detailsOptical.docType == 0) {
            Toast.makeText(requireContext(), "DocType is ${documentReaderResults.status.detailsOptical.docType}", Toast.LENGTH_SHORT).show()
        }
        if (documentReaderResults.status.detailsOptical.text == 0) {
            Toast.makeText(requireContext(), "Text is ${documentReaderResults.status.detailsOptical.text}", Toast.LENGTH_SHORT).show()
        }

        if (documentReaderResults.textResult == null) {
            Toast.makeText(requireContext(), "Please scan a valid document", Toast.LENGTH_SHORT)
                .show()
            return false
        } else return true


//        if (documentReaderResults.imageQuality.size > 0) {

//            documentReaderResults.imageQuality.get(0).imageQualityList.apply {
//                if (get(2).result != 1) {
//                    showToast("Image resolution is below threshold")
//                    return false
//                } else if (get(5).result != 1) {
//                    showToast("Document is not fully present in the image")
//                    return false
//                } else if (get(7).result != 1) {
//                    showToast("The portrait is not present")
//                    return false
//                } else
//                if (get(eImageQualityCheckType.IQC_IMAGE_COLORNESS).result != 1) {
//                    showToast("Image is colorless")
//                    return true
//                } else return true
//            }
//        } else {
//            showToast("Unable to get Quality Image")
//            return false
//        }
//        if (documentReaderResults.textResult?.comparisonStatus == 0) {
//            Toast.makeText(requireContext(), "Please make sure rear image is of the same document", Toast.LENGTH_LONG).show()
//            return
//        }

    }

    private fun postRequest(jsonInputString: String) {
        loadingDialog = showDialog("Getting results from server")
        MainActivity.ENCRYPTED_RESULT_SERVICE
            .httpPost()
            .header(mapOf("Content-Type" to "application/json; utf-8"))
            .body(jsonInputString)
            .responseString { _, _, result ->
                hideDialog()
                when (result) {
                    is Result.Success -> {
                        val map = result.component1()
                            ?.let { DocReaderResultsJsonParser.parseCoreResults(it) }
                        val resultsNew = map?.get("docReaderResults") as DocumentReaderResults
                        MainScreenViewpagerFragment.results = MainScreenViewpagerFragment.results

//                        findNavController().navigate(R.id.selfieTimeFragment)
                        // startActivity(Intent(this, ResultsActivity::class.java))
                    }
                    is Result.Failure -> {
                        println(result.getException())
                        activity?.runOnUiThread {
                            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                                .setTitle("Something went wrong")
                                .setMessage("Check your internet connection and try again")
                                .setPositiveButton("Retry") { _, _ ->
                                    postRequest(jsonInputString)
                                }
                                .setNegativeButton("Cancel", null)
                                .show()
                        }
                    }
                }
            }
    }

    private fun cancelAnimation() {
        mTimerAnimator?.let {
            it.cancel()
            isAnimationStarted = false
            mTimerAnimator = null
        }
        DocumentReader.Instance().customization().edit().setUiCustomizationLayer(null).apply()
    }

    @SuppressLint("WrongConstant")
    fun showScanner() {
        typeOfData=1
        MainActivity.currentScenario = Scenario.SCENARIO_FULL_AUTH
        val scannerConfig = ScannerConfig.Builder(Scenario.SCENARIO_FULL_AUTH).build()
        DocumentReader.Instance().showScanner(requireContext(), scannerConfig, completion)
    }
    @SuppressLint("WrongConstant")
    fun showBankCard() {
        typeOfData=2
        MainActivity.currentScenario = Scenario.SCENARIO_CREDIT_CARD
        val scannerConfig = ScannerConfig.Builder(Scenario.SCENARIO_CREDIT_CARD).build()
        DocumentReader.Instance().showScanner(requireContext(), scannerConfig, completion)
    }
    @SuppressLint("WrongConstant")
    fun scanMrz() {
        typeOfData=1
        MainActivity.currentScenario = Scenario.SCENARIO_MRZ
        val scannerConfig = ScannerConfig.Builder(Scenario.SCENARIO_MRZ).build()
        DocumentReader.Instance().showScanner(requireContext(), scannerConfig, completion)
    }
    @SuppressLint("WrongConstant")
    fun scanBarcode() {
        typeOfData=1
        MainActivity.currentScenario = Scenario.SCENARIO_BARCODE
        val scannerConfig = ScannerConfig.Builder(Scenario.SCENARIO_BARCODE).build()
        DocumentReader.Instance().showScanner(requireContext(), scannerConfig, completion)
    }
    @SuppressLint("WrongConstant")
    fun scanOCR() {
        typeOfData=1
        MainActivity.currentScenario = Scenario.SCENARIO_OCR
        val scannerConfig = ScannerConfig.Builder(Scenario.SCENARIO_OCR).build()
        DocumentReader.Instance().showScanner(requireContext(), scannerConfig, completion)
    }

    private fun hideDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
    private fun showDialog(msg: String): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.background = ResourcesCompat.getDrawable(resources, R.drawable.rounded, requireContext().theme)
        dialog.setTitle(msg)
        dialog.setView(layoutInflater.inflate(R.layout.simple_dialog, binding.root, false))
        dialog.setCancelable(false)
        return dialog.show()
    }


    private fun setProcessingPreferences() {
        val pref = SharedPref(requireContext())


        //DocumentReader.Instance().functionality().edit().setManualMultipageMode(false).apply()
//        DocumentReader.Instance().processParams().multipageProcessing = false
//
//        val authenticityParams = AuthenticityParams.defaultParams()
//
//        authenticityParams.livenessParams = LivenessParams.defaultParams()
//
//        authenticityParams.livenessParams?.checkED = true
//
//        DocumentReader.Instance().processParams().processAuth =

        DocumentReader.Instance().functionality().edit().setShowSkipNextPageButton(false).apply()
        DocumentReader.Instance().rfidScenario().isAutoSettings = true
        DocumentReader.Instance().functionality().edit().setDisplayMetadata(pref.getBoolean(Constants.SHOW_METADATA)).apply()

        DocumentReader.Instance().processParams().apply {
            respectImageQuality = true
            debugSaveLogs = true
            debugSaveCroppedImages = true
            debugSaveRFIDSession = true
            rfidParams
            checkRequiredTextFields = true
            imageQA.colornessCheck = pref.getBoolean(Constants.COLOR)
            imageQA.glaresCheck = pref.getBoolean(Constants.GLARES)
            imageQA.focusCheck = pref.getBoolean(Constants.FOCUS)
            currentScenario = scenario
//            multipageProcessing = pref.getDefaultTrue(Constants.MULTIPAGE_PROCESSING)

        }

        DocumentReader.Instance().functionality().edit().setRfidTimeout(5).apply()

//        val authenticityParams = DocumenAuthenticityParams.defaultParams()
//
//        authenticityParams.livenessParams = LivenessParams.defaultParams()
//
//        authenticityParams.livenessParams?.checkHolo = true
//
//        DocumentReader.Instance().processParams().authenticityParams = authenticityParams

        DocumentReader.Instance().processParams().processAuth
        DocumentReader.Instance().processParams().multipageProcessing = pref.getDefaultTrue(Constants.MULTIPAGE_PROCESSING)
        DocumentReader.Instance().processParams().doublePageSpread = pref.getBoolean(Constants.DOUBLE_PAGE_SPREAD_PROCESSING)

        DocumentReader.Instance().functionality().edit().setShowCaptureButton(pref.getBoolean(Constants.CAPTURE_BUTTON)).apply()
        DocumentReader.Instance().functionality().edit().setShowCameraSwitchButton(pref.getBoolean(Constants.CAMERA_SWITCH_BUTTON)).apply()
        DocumentReader.Instance().customization().edit().setShowHelpAnimation(pref.getBoolean(Constants.HELP)).apply()
        DocumentReader.Instance().functionality().edit().setVideoCaptureMotionControl(pref.getBoolean(Constants.MOTION_DETECTION)).apply()
        DocumentReader.Instance().functionality().edit().setSkipFocusingFrames(pref.getBoolean(Constants.FOCUSING_DETECTION)).apply()
        DocumentReader.Instance().functionality().edit().setZoomEnabled(pref.getBoolean(Constants.ADJUST_ZOOM_LEVEL)).apply()
        DocumentReader.Instance().processParams().manualCrop = pref.getBoolean(Constants.MANUAL_CROP)
        DocumentReader.Instance().processParams().integralImage = pref.getBoolean(Constants.INTERNAL_IMAGE)
        DocumentReader.Instance().processParams().checkHologram = pref.getBoolean(Constants.HOLOGRAM_DETECTION)
        if (!pref.getString(Constants.TIME_OUT).equals(""))
            DocumentReader.Instance().processParams().timeout = pref.getString(Constants.TIME_OUT).toDouble()
        if (!pref.getString(Constants.TIME_OUT_STARTING_FROM_DOCUMENT_TYPE_IDENTIFICATION).equals(""))
            DocumentReader.Instance().processParams().timeoutFromFirstDocType=pref.getString(Constants.TIME_OUT_STARTING_FROM_DOCUMENT_TYPE_IDENTIFICATION).toDouble()
        if (!pref.getString(Constants.TIMEOUT_STARTING_FROM_DOCUMENT_DETECTION).equals(""))
            DocumentReader.Instance().processParams().timeoutFromFirstDetect=pref.getString(Constants.TIMEOUT_STARTING_FROM_DOCUMENT_DETECTION).toDouble()
        DocumentReader.Instance().functionality().edit().setZoomEnabled(pref.getBoolean(Constants.ZOOM_LEVEL)).apply()
        if (!pref.getString(Constants.MINIMUM_DPI).equals(""))
            DocumentReader.Instance().processParams().minDPI=pref.getString(Constants.MINIMUM_DPI).toInt()
        if (!pref.getString(Constants.PERPECTIVE_ANGLE).equals(""))
            DocumentReader.Instance().processParams().perspectiveAngle=pref.getString(Constants.PERPECTIVE_ANGLE).toInt()
      //  DocumentReader.Instance().functionality().edit().setCameraSize(pref.getString(Constants.CAMERA_RESOLUTION)).apply()
        DocumentReader.Instance().processParams().dateFormat=pref.getString(Constants.DATE_FORMAT)



        when(pref.getString(Constants.PROCESSING_MODES)) {
            getString(R.string.video_processing) -> {
                DocumentReader.Instance().functionality().edit().setCaptureMode(CaptureMode.CAPTURE_VIDEO)
            }
            getString(R.string.frame_processing) -> {
                DocumentReader.Instance().functionality().edit().setCaptureMode(CaptureMode.CAPTURE_FRAME)
            }
            getString(R.string.autos) -> {
                DocumentReader.Instance().functionality().edit().setCaptureMode(CaptureMode.AUTO)
            }
        }
        when(pref.getString(Constants.CAMERA_API)) {
            getString(R.string.camera_apis) -> {
                DocumentReader.Instance().functionality().edit().setCameraMode(CameraMode.CAMERA1).apply()
            }
            getString(R.string.camera2_api) -> {
                DocumentReader.Instance().functionality().edit().setCameraMode(CameraMode.CAMERA2).apply()
            }
            getString(R.string.auto_selection) -> {
                DocumentReader.Instance().functionality().edit().setCameraMode(CameraMode.AUTO).apply()
            }
        }
        DocumentReader.Instance().rfidScenario().apply {
            isPkdDSCertPriority = pref.getBoolean(Constants.PRIORITY_OF_USING_DS_CERTIFICATES)
            isPkdUseExternalCSCA = pref.getBoolean(Constants.USE_OF_EXTERNAL_CSCA_CERTIFICATES)
            isTrustedPKD = pref.getBoolean(Constants.TRUST_PKD_CERTIFICATES)
            isPassiveAuth = pref.getBoolean(Constants.PASSIVE_AUTHENTICATION)
            isSkipAA = pref.getBoolean(Constants.DO_NOT_PERFORM_AA_AFTER_CA)
            isStrictProcessing = pref.getBoolean(Constants.STRICT_ISO_PROTOCOL)

            when(pref.getString(Constants.AUTHENTICATION_PROCEDURE_TYPE)) {
                getString(R.string.standard_inspection_procedure) -> {
                    DocumentReader.Instance().rfidScenario().setAuthProcType(
                        eRFID_AuthenticationProcedureType.aptStandard)
                }
                getString(R.string.advance_inspection_procedure) -> {
                    DocumentReader.Instance().rfidScenario().setAuthProcType(
                        eRFID_AuthenticationProcedureType.aptAdvanced)
                }
                getString(R.string.general_inspection_procedure) -> {
                    DocumentReader.Instance().rfidScenario().setAuthProcType(
                        eRFID_AuthenticationProcedureType.aptGeneral)

                }
            }
            when(pref.getString(Constants.BASIC_SECURITY_MESSAGING_PROCEDURE)) {
                getString(R.string.bac) -> {
                    DocumentReader.Instance().rfidScenario().setBaseSMProcedure(
                        eRFID_AccessControl_ProcedureType.ACPT_BAC)
                }
                getString(R.string.pacee) -> {
                    DocumentReader.Instance().rfidScenario().setBaseSMProcedure(
                        eRFID_AccessControl_ProcedureType.ACPT_PACE)
                }
            }
            when(pref.getString(Constants.DATA_ACCESS_KEY)) {
                getString(R.string.mrzs) -> {
                    DocumentReader.Instance().rfidScenario().setPacePasswordType(
                        eRFID_Password_Type.PPT_MRZ)
                }
                getString(R.string.can) -> {
                    DocumentReader.Instance().rfidScenario().setPacePasswordType(
                        eRFID_Password_Type.PPT_CAN)
                }
                getString(R.string.pin) -> {
                    DocumentReader.Instance().rfidScenario().setPacePasswordType(
                        eRFID_Password_Type.PPT_PIN)
                }
                getString(R.string.puk) -> {
                    DocumentReader.Instance().rfidScenario().setPacePasswordType(
                        eRFID_Password_Type.PPT_PUK)
                }
                getString(R.string.sai) -> {
                    DocumentReader.Instance().rfidScenario().setPacePasswordType(
                        eRFID_Password_Type.PPT_SAI)
                }
            }
            when(pref.getString(Constants.PROFILE_TYPE)) {
                getString(R.string.doc_9303_6th_edition_2006) -> {
                    DocumentReader.Instance().rfidScenario().setProfilerType(
                        eRFID_SDK_ProfilerType.SPT_DOC_9303_EDITION_2006)
                }
                getString(R.string.lds_and_pki_maintenance_v2_0_may_21) -> {
                    DocumentReader.Instance().rfidScenario().setProfilerType(
                        eRFID_SDK_ProfilerType.SPT_DOC_9303_LDS_PKI_MAINTENANCE)
                }
            }
        }

        DocumentReader.Instance().rfidScenario().apply {
            isReadEPassport = pref.getBoolean(Constants.READ_E_PASSPORT)
            if (pref.getBoolean(Constants.READ_E_PASSPORT)) {
                ePassportDataGroups().apply {
                    isDG1 = pref.getBoolean(Constants.MACHINE_READABLE_ZONE_DG1)
                    isDG2 = pref.getBoolean(Constants.BIOMETRY_FACIAL_DATA_DG2)
                    isDG3 = pref.getBoolean(Constants.BIOMETRY_FINGERPRINT_DG3)
                    isDG4 = pref.getBoolean(Constants.BIOMETRY_IRIS_DATA_DG4)
                    isDG5 = pref.getBoolean(Constants.PORTRAIT_DG5)
                    isDG6 = pref.getBoolean(Constants.E_PASSPORT_DG6)
                    isDG7 = pref.getBoolean(Constants.SIGNATURE_DG7)
                    isDG8 = pref.getBoolean(Constants.E_PASSPORT_DG8)
                    isDG9 = pref.getBoolean(Constants.E_PASSPORT_DG9)
                    isDG10 = pref.getBoolean(Constants.E_PASSPORT_DG10)
                    isDG11 = pref.getBoolean(Constants.E_PASSPORT_DG11)
                    isDG12 = pref.getBoolean(Constants.E_PASSPORT_DG12)
                    isDG13 = pref.getBoolean(Constants.E_PASSPORT_DG13)
                    isDG14 = pref.getBoolean(Constants.E_PASSPORT_DG14)
                    isDG15 = pref.getBoolean(Constants.E_PASSPORT_DG15)
                    isDG16 = pref.getBoolean(Constants.MACHINE_READABLE_ZONE_DG1)
                    isDG1 = pref.getBoolean(Constants.E_PASSPORT_DG16)
                }
            }
//      Read eID
            isReadEID = pref.getBoolean(Constants.READ_E_ID)
            if (pref.getBoolean(Constants.READ_E_ID)) {
                eIDDataGroups().apply {
                    isDG1 = pref.getBoolean(Constants.E_ID_DG1)
                    isDG2 = pref.getBoolean(Constants.E_ID_DG2)
                    isDG3 = pref.getBoolean(Constants.E_ID_DG3)
                    isDG4 = pref.getBoolean(Constants.E_ID_DG4)
                    isDG5 = pref.getBoolean(Constants.E_ID_DG5)
                    isDG6 = pref.getBoolean(Constants.E_ID_DG6)
                    isDG7 = pref.getBoolean(Constants.E_ID_DG7)
                    isDG8 = pref.getBoolean(Constants.E_ID_DG8)
                    isDG9 = pref.getBoolean(Constants.E_ID_DG9)
                    isDG10 = pref.getBoolean(Constants.E_ID_DG10)
                    isDG11 = pref.getBoolean(Constants.E_ID_DG11)
                    isDG12 = pref.getBoolean(Constants.E_ID_DG12)
                    isDG13 = pref.getBoolean(Constants.E_ID_DG13)
                    isDG14 = pref.getBoolean(Constants.E_ID_DG14)
                    isDG15 = pref.getBoolean(Constants.E_ID_DG15)
                    isDG16 = pref.getBoolean(Constants.E_ID_DG16)
                    isDG17 = pref.getBoolean(Constants.E_ID_DG17)
                    isDG18 = pref.getBoolean(Constants.E_ID_DG18)
                    isDG19 = pref.getBoolean(Constants.E_ID_DG19)
                    isDG20 = pref.getBoolean(Constants.E_ID_DG20)
                    isDG21 = pref.getBoolean(Constants.E_ID_DG21)
                }
            }
//      Read eDL
            isReadEDL = pref.getBoolean(Constants.READ_E_DL)
            if (pref.getBoolean(Constants.READ_E_DL)) {
                eDLDataGroups().apply {
                    isDG1 = pref.getBoolean(Constants.E_DL_DG1)
                    isDG2 = pref.getBoolean(Constants.E_DL_DG2)
                    isDG3 = pref.getBoolean(Constants.E_DL_DG3)
                    isDG4 = pref.getBoolean(Constants.E_DL_DG4)
                    isDG5 = pref.getBoolean(Constants.E_DL_DG5)
                    isDG6 = pref.getBoolean(Constants.E_DL_DG6)
                    isDG7 = pref.getBoolean(Constants.E_DL_DG7)
                    isDG8 = pref.getBoolean(Constants.E_DL_DG8)
                    isDG9 = pref.getBoolean(Constants.E_DL_DG9)
                    isDG10 = pref.getBoolean(Constants.E_DL_DG10)
                    isDG11 = pref.getBoolean(Constants.E_DL_DG11)
                    isDG12 = pref.getBoolean(Constants.E_DL_DG12)
                    isDG13 = pref.getBoolean(Constants.E_DL_DG13)
                    isDG14 = pref.getBoolean(Constants.E_DL_DG14)
                }
            }
        }

    }


    @SuppressLint("WrongConstant")
    fun rfidProgress(code: Int, value: Int) {
        val hiword = code and -0x10000
        val loword = code and 0x0000FFFF
        when (hiword) {
            eRFID_NotificationCodes.RFID_NOTIFICATION_PCSC_READING_DATAGROUP -> if (value == 0) {
                Log.d("Rfid", "Current group: " + String.format(
                    getString(com.regula.documentreader.api.R.string.strReadingRFIDDG),
                    eRFID_DataFile_Type.getTranslation(
                        requireContext().applicationContext, loword
                    )))
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.rlFullProcessing->{
                isFaceMatchingEnabled = true
//                getSessionId()
                showScanner()
            }
            R.id.rlBankCard->{
                isFaceMatchingEnabled = false
//                getSessionId()
                showBankCard()
            }
            R.id.rlMRZ->{
                isFaceMatchingEnabled = false
//                getSessionId()
                scanMrz()
            }
            R.id.rlBarcode->{
                isFaceMatchingEnabled = false
//                getSessionId()
                scanBarcode()
            }
            R.id.rlOcr->{
                isFaceMatchingEnabled = false
//                getSessionId()
                scanOCR()
            }
        }
    }
}