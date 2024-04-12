package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.aml_manual.AmlManualRequest
import com.sdk.ipassplussdk.model.request.check_face_analysis.CheckFaceAnalysisRequest
import com.sdk.ipassplussdk.model.request.facesimilarity.FaceSimilarityRequest
import com.sdk.ipassplussdk.model.request.livenesscheck.LivenessCheckRequest
import com.sdk.ipassplussdk.model.request.login.LoginRequest
import com.sdk.ipassplussdk.model.request.regula.regula_post_data.OcrPostdataRequest
import com.sdk.ipassplussdk.model.request.session_create.SessionCreateRequest
import com.sdk.ipassplussdk.model.request.session_result.SessionResultRequest
import com.sdk.ipassplussdk.model.request.valid_api.Image
import com.sdk.ipassplussdk.model.request.valid_api.ValidApiRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.aml_manual.AmlManualResponse
import com.sdk.ipassplussdk.model.response.check_face_analysis.CheckFaceAnalysisResponse
import com.sdk.ipassplussdk.model.response.facesimilarity.FaceSimilarityResponse
import com.sdk.ipassplussdk.model.response.livenesscheck.LivenessCheckResponse
import com.sdk.ipassplussdk.model.response.login.LoginResponse
import com.sdk.ipassplussdk.model.response.regula.regula_post_data.OcrPostDataResponse
import com.sdk.ipassplussdk.model.response.session_create.Data
import com.sdk.ipassplussdk.model.response.session_result.SessionResultResponse
import com.sdk.ipassplussdk.model.response.valid_api.ValidApiResponse
import com.sdk.ipassplussdk.ui.AwsFaceRekognition
import com.sdk.ipassplussdk.ui.AwsFaceRekognition.initFaceDetector
import com.sdk.ipassplussdk.ui.DocumentReaderData
import com.sdk.ipassplussdk.ui.InitializeDatabase
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.InternetConnectionService
import com.sdk.ipassplussdk.utils.Scenarios
import com.sdk.ipassplussdk.views.Components.showProgressDialog

object IPassSDK {

    private val sid = "111"
    private var auth_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjYxOTMzMThmOTFjYTM5ZTliNDk4Yjg0IiwiZW1haWwiOiJpcGFzc21vYmlsZWNzQHlvcG1haWwuY29tIiwiaWF0IjoxNzEyOTI5NTU2LCJleHAiOjE3MTI5MzEzNTZ9.0srHf-RvczZAjS_ulZHbzbWJ9XwRKL2GyACj03_iW5o"
    private var rawResult = ""

    private val im1 = ""
    private val im2 = ""

    private var sessionId = ""

    private var progressDialog: AlertDialog? = null
    fun setAuthToken(token: String) {
        auth_token = token
    }
    fun setRawResult(rawResult: String) {
        this.rawResult = rawResult
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLoginRequest(
        context: Context,
//        request: LoginRequest,
        completion: ResultListener<LoginResponse>
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }

//        if (request.email.isNullOrEmpty()) {
//            completion.onError("email is empty")
//            return
//        }
//
//        if (request.password.isNullOrEmpty()) {
//            completion.onError("password is empty")
//            return
//        }
        val request = LoginRequest(Constants.EMAIL, Constants.PASSWORD)

        LoginData.login(context, request, completion)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun faceSessionCreateRequest(
        context: Context,
        bindingView: ViewGroup,
//        token: String,
//        request: SessionCreateRequest,
//        completion: ResultListener<FaceSessionCreateResponse>
        callback: (String) -> Unit
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
//            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }

        val request = SessionCreateRequest()
        request.email = Constants.EMAIL
        request.authToken = auth_token

//                                val requestS = FaceSimilarityRequest()
//                        requestS.sid = sid
//                        requestS.email = Constants.EMAIL
//                        requestS.authToken = auth_token
//                        requestS.sourceImageBase64 = im1
//                        requestS.targetImageBase64 = im2

//        FaceSimilarityData.facesimilarity(context, Constants.TOKEN, requestS, completion)

        SessionCreateData.sessionCreate(context, Constants.TOKEN, bindingView, request, object : ResultListener<Data> {
            override fun onSuccess(response: Data?) {
                sessionId = response?.sessionId!!
                initFaceDetector(context, sessionId, bindingView) {
                    if (it.equals("success")) {
                        val sessionResultRequest = SessionResultRequest()
                        sessionResultRequest.authToken = auth_token

                        SessionResultData.sessionResult(context, Constants.TOKEN, sessionId, sid, Constants.EMAIL, sessionResultRequest, completion)
//                        val requestS = LivenessCheckRequest()
//                        requestS.auth_token = auth_token
////                        requestS.email = Constants.EMAIL
////                        requestS.imageBase64 = auth_token
////                        requestS.sourceImageBase64 = im1
////                        requestS.targetImageBase64 = im2
//                        LivenessCheckData.livenessCheck(context, Constants.TOKEN, requestS, completion)
                    } else callback.invoke(it)
                }
            }

            override fun onError(exception: String) {
                callback.invoke(exception)
            }

        })
    }

    val completion = object : ResultListener<SessionResultResponse> {
        override fun onSuccess(response: SessionResultResponse?) {
            println(response)
        }

        override fun onError(exception: String) {
            TODO("Not yet implemented")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun faceSessionResultRequest(
        context: Context,
        token: String,
        sessionId: String,
        sid: String,
        email: String,
        request: SessionResultRequest,
        completion: ResultListener<BaseModel<SessionResultResponse>>
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }

//        SessionResultData.sessionResult(context, token, sessionId, sid, email, request, completion)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getLivenessCheckRequest(
        context: Context,
        token: String,
        request: LivenessCheckRequest,
        completion: ResultListener<LivenessCheckResponse>
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }

//        if (request.imageBase64.isNullOrEmpty()) {
//            completion.onError("image is empty")
//            return
//        }

//        LivenessCheckData.livenessCheck(context, token, request, completion)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFaceSimilarityRequest(
        context: Context,
        token: String,
        request: FaceSimilarityRequest,
        completion: ResultListener<com.sdk.ipassplussdk.model.response.facesimilarity.Data>
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }

        if (request.sourceImageBase64.isNullOrEmpty()) {
            completion.onError("source image is empty")
            return
        }

        if (request.targetImageBase64.isNullOrEmpty()) {
            completion.onError("target image is empty")
            return
        }

        FaceSimilarityData.facesimilarity(context, token, request, completion)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadOcrDocuments(
        context: Context,
        token: String,
        request: OcrPostdataRequest,
        completion: ResultListener<OcrPostDataResponse>
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }

        if (request.image1.isNullOrEmpty()) {
            completion.onError("Please provide Front page")
            return
        }

//        if (request.image2.isNullOrEmpty()) {
//            completion.onError("target image is empty")
//            return
//        }

        OcrPostData.ocrPostData(context, token, request, completion)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkFaceAnalysisRequest(
        context: Context,
        token: String,
        token1: String,
        request: CheckFaceAnalysisRequest,
        completion: ResultListener<BaseModel<CheckFaceAnalysisResponse>>
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }

        if (request.imageBase64.isNullOrEmpty()) {
            completion.onError("image is empty")
            return
        }

        CheckFaceAnalysisData.checkFaceAnalysis(context, token, token1, request, completion)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun amlManualRequest(
        context: Context,
        token: String,
//        request: AmlManualRequest,
        completion: ResultListener<AmlManualResponse>
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }

        val request = AmlManualRequest()
        request.sid = sid
        request.email = Constants.EMAIL
        request.custEmail = "anuj12@gmail.com"
        request.entityName = "YAZAN MUNIR MOHAMMAD"
        request.fuzLevel = "30"
        request.authToken = auth_token

        AmlManualData.amlManual(context, Constants.TOKEN, request, completion)

    } @RequiresApi(Build.VERSION_CODES.O)
    fun validApiRequest(
        context: Context,
        token: String,
        request: ValidApiRequest,
        completion: ResultListener<ValidApiResponse>
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            completion.onError(Constants.NO_INTERNET_TEXT)
            return
        }
//        val request = ValidApiRequest()
//        request.sid = sid
//        request.email = Constants.EMAIL
//        request.applicationId = "4845116"
//
//        val image1 = Image()
//        image1.image = img1
//        image1.type = "FRONT"
//
//        val image2 = Image()
//        image2.image = img2
//        image2.type = "BACK"

//        request.image = arrayListOf(image1, image2)
//        request.authToken = auth_token

        ValidApiData.validApiData(context, token, request, completion)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showScannerRequest(
        context: Context,
//        sid:String,
        custEmail: String,
//        auth_token: String,
    ) {
        DocumentReaderData.showScanner(context, sid, custEmail, auth_token)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun initiliazeDatabase(context: Context, message: (String) -> Unit){
        progressDialog = showProgressDialog(context,"Initializing")
        InitializeDatabase.InitDatabase(context, progressDialog) {
            if (it.equals("success")) {
                progressDialog?.setTitle("Configuring Aws")
                AwsFaceRekognition.configureAws(context) {
                    if (it.equals("awsConfigured")) {
                        progressDialog?.setTitle("Authenticating")
                        val request = LoginRequest(Constants.EMAIL, Constants.PASSWORD)
                        LoginData.login(context, request, object : ResultListener<LoginResponse> {
                            override fun onSuccess(response: LoginResponse?) {
                                auth_token = response?.user?.token!!
                                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                                message.invoke("Database initialized Successfully")
                            }
                            override fun onError(exception: String) {
                                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                                message.invoke("Error : $exception ")
                            }
                        })
                    } else {
                        if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                        message.invoke("Error : $it")
                    }
                }
            } else {
                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                message.invoke("Error : $it")
            }
        }
    }

//    fun ConfigureAws(context: Context){
//        AwsFaceRekognition.configureAws(context)
//    }

    fun FaceDetector(context: Context, sessionId: String,bindingView:ViewGroup){
        AwsFaceRekognition.initFaceDetector(context, sessionId, bindingView) {

        }
    }

    fun getScenariosList() {
        val list = arrayListOf(Scenarios.SCENARIO_FULL_PROCESS)
    }



}
