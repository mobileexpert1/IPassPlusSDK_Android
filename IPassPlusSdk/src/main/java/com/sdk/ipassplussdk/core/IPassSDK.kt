package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.gson.JsonParser
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.login.LoginRequest
import com.sdk.ipassplussdk.model.request.session_create.SessionCreateRequest
import com.sdk.ipassplussdk.model.request.session_result.SessionResultRequest
import com.sdk.ipassplussdk.model.response.data_save.DataSaveRequest
import com.sdk.ipassplussdk.model.response.data_save.DataSaveResponse
import com.sdk.ipassplussdk.model.response.data_save.Livenessdata
import com.sdk.ipassplussdk.model.response.document_scanner.DocumentScannerResponse
import com.sdk.ipassplussdk.model.response.liveness_facesimilarity.FaceScannerResponse
import com.sdk.ipassplussdk.model.response.login.LoginResponse
import com.sdk.ipassplussdk.model.response.session_create.Data
import com.sdk.ipassplussdk.resultCallbacks.InitializeDatabaseCompletion
import com.sdk.ipassplussdk.ui.FaceScannerData
import com.sdk.ipassplussdk.ui.FaceScannerData.initFaceDetector
import com.sdk.ipassplussdk.ui.DocumentReaderData
import com.sdk.ipassplussdk.ui.InitializeDatabase
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.InternetConnectionService
import com.sdk.ipassplussdk.utils.Scenarios
import java.util.UUID

object IPassSDK {

    private var sid = ""
//    private var auth_token = ""
    private var rawResult: String? = null


//    init face detection
    @RequiresApi(Build.VERSION_CODES.O)
    private fun faceSessionCreateRequest(
        context: Context,
        email: String,
        authToken: String,
        appToken: String,
        bindingView: ViewGroup,
        callback: (Boolean, String) -> Unit
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            return
        }

        val request = SessionCreateRequest()
        request.email = email
        request.authToken = authToken

        SessionCreateData.sessionCreate(context, appToken, request, object : ResultListener<Data> {
            override fun onSuccess(response: Data?) {
                val sessionId = response?.sessionId!!
                showFaceScanner(context, email, authToken, appToken, sessionId, bindingView, callback)
            }
            override fun onError(exception: String) {
                callback.invoke(false, exception)
            }
        })
    }

//    show scanner to detect face liveness
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showFaceScanner(
    context: Context,
    email: String,
    authToken: String,
    appToken: String,
    sessionId: String,
    bindingView: ViewGroup,
    callback: (Boolean, String) -> Unit
) {
        initFaceDetector(context, sessionId, bindingView) {
            if (it.equals("success")) {
                getSessionResult(context, email, authToken, appToken, sessionId, callback)
            } else callback.invoke(false, it)
        }
    }

//    get liveness data and images from aws
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getSessionResult(context: Context,
                                 email: String,
                                 authToken: String,
                                 appToken: String,
                                 sessionId: String,
                                 callback: (Boolean, String) -> Unit) {
        val sessionResultRequest = SessionResultRequest()
        sessionResultRequest.authToken = authToken
        SessionResultData.sessionResult(context, appToken, sessionId, sid, email, sessionResultRequest, object : ResultListener<Livenessdata> {
            override fun onSuccess(response: Livenessdata?) {
                postDataToServer(context, email, response, callback)
            }
            override fun onError(exception: String) {
                callback.invoke(false, exception)
            }
        })
    }

//    post scanned data to server
    @RequiresApi(Build.VERSION_CODES.O)
    private fun postDataToServer(
        context: Context,
        email: String,
        response: Livenessdata?,
        callback: (Boolean, String) -> Unit
    ) {
        val postdataRequest = DataSaveRequest()
        postdataRequest.email = email
        postdataRequest.randomid = sid
        val rawData = JsonParser().parse(rawResult).asJsonObject
        postdataRequest.idvData = rawData
        postdataRequest.livenessdata = (response as Livenessdata)

        PostAllData.postAllData(context, postdataRequest, object : ResultListener<DataSaveResponse> {
            override fun onSuccess(response: DataSaveResponse?) {
                callback.invoke(true, response?.message!!)
            }
            override fun onError(exception: String) {
                callback.invoke(false, exception)
            }
        })
    }

    //    Show scanner for document verification
    @RequiresApi(Build.VERSION_CODES.O)
    fun showScannerRequest(
        context: Context,
        email: String,
        authToken: String,
        appToken: String,
        bindingView: ViewGroup,
        callback: (status: Boolean, message: String) -> Unit
    ) {
        sid = getSid()
        DocumentReaderData.showScanner(context) {
            status, message ->
            if (status) {
                this.rawResult = message
                faceSessionCreateRequest(context, email, authToken, appToken, bindingView) {
                    statusF, messageF ->
                    callback.invoke(statusF, messageF)
                }
            } else {
                callback.invoke(false, message)
            }

        }
    }

//    initialize database for scanning (needs to be initialized at least once before scanning)
    @RequiresApi(Build.VERSION_CODES.O)
    fun initializeDatabase(context: Context, completion: InitializeDatabaseCompletion){

        InitializeDatabase.InitDatabase(context, object : InitializeDatabaseCompletion {
            override fun onProgressChanged(progress: Int) {
                completion.onProgressChanged(progress)
            }

            override fun onCompleted(status: Boolean, message: String?) {
                if (status) {
                    configureFaceScanner(context, completion)
                } else {
                    completion.onCompleted(status, message)
                }
            }

        })
    }

//    Face scanner configuration
    @RequiresApi(Build.VERSION_CODES.O)
    private fun configureFaceScanner(context: Context, completion: InitializeDatabaseCompletion) {
        FaceScannerData.configureFaceScanner(context) {
            if (it.equals("FaceScannerConfigured")) {
                completion.onCompleted(true, "Database Initialized Successfully")
            } else {
                completion.onCompleted(false, it)
            }
        }
    }

//    Request Auth Token
    @RequiresApi(Build.VERSION_CODES.O)
     fun getAuthToken(context: Context, email: String, password: String, completion: ResultListener<LoginResponse>) {
        val request = LoginRequest(email, password)
        LoginData.login(context, request, completion)
    }

    //    returns a unique sId for every scan
    private fun getSid(): String {
        // Generate a random UUID
        val myUuid = UUID.randomUUID()
        val myUuidAsString = myUuid.toString()
        Log.e("Sid", myUuidAsString)

        return myUuidAsString
    }

//    returns a list of available Processing Scenarios
    fun getScenariosList() {
        val list = arrayListOf(Scenarios.SCENARIO_FULL_PROCESS)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFaceScannerData(context: Context, appToken: String, completion: ResultListener<FaceScannerResponse>) {
        GetResults.FaceScannerResult(context, appToken, sid, completion)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDocumentScannerData(context: Context, appToken: String, completion: ResultListener<DocumentScannerResponse>) {
        GetResults.DocumentScanerResult(context, appToken, sid, completion)
    }


}
