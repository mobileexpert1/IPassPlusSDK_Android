package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.gson.JsonParser
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.login.LoginRequest
import com.sdk.ipassplussdk.model.request.session_create.SessionCreateRequest
import com.sdk.ipassplussdk.model.request.session_result.SessionResultRequest
import com.sdk.ipassplussdk.model.response.data_save.DataSaveRequest
import com.sdk.ipassplussdk.model.response.data_save.DataSaveResponse
import com.sdk.ipassplussdk.model.response.data_save.Livenessdata
import com.sdk.ipassplussdk.model.response.login.LoginResponse
import com.sdk.ipassplussdk.model.response.session_create.Data
import com.sdk.ipassplussdk.ui.FaceScannerData
import com.sdk.ipassplussdk.ui.FaceScannerData.initFaceDetector
import com.sdk.ipassplussdk.ui.DocumentReaderData
import com.sdk.ipassplussdk.ui.InitializeDatabase
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.InternetConnectionService
import com.sdk.ipassplussdk.utils.Scenarios
import com.sdk.ipassplussdk.views.Components.showProgressDialog
import java.util.UUID

object IPassSDK {

    private var sid = ""
    private var auth_token = ""
    private var rawResult: String? = null
    private var sessionId = ""
    private var progressDialog: AlertDialog? = null

    fun setRawResult(rawResult: String) {
        this.rawResult = rawResult
    }


//    init face detection
    @RequiresApi(Build.VERSION_CODES.O)
    private fun faceSessionCreateRequest(
        context: Context,
        bindingView: ViewGroup,
        callback: (String) -> Unit
    ) {
        if (!InternetConnectionService.networkAvailable(context)) {
            return
        }

        val request = SessionCreateRequest()
        request.email = Constants.EMAIL
        request.authToken = auth_token

        SessionCreateData.sessionCreate(context, Constants.TOKEN, bindingView, request, object : ResultListener<Data> {
            override fun onSuccess(response: Data?) {
                sessionId = response?.sessionId!!
                showFaceScanner(context, bindingView, callback)
            }
            override fun onError(exception: String) {
                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                callback.invoke(exception)
            }
        })
    }

//    show scanner to detect face liveness
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showFaceScanner(
        context: Context,
        bindingView: ViewGroup,
        callback: (String) -> Unit
    ) {
        if (progressDialog?.isShowing!!) progressDialog?.dismiss()
        initFaceDetector(context, sessionId, bindingView) {
            if (it.equals("success")) {
                getSessionResult(context, callback)
            } else callback.invoke(it)
        }
    }

//    get liveness data and images from aws
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getSessionResult(context: Context, callback: (String) -> Unit) {
        progressDialog = showProgressDialog(context, "Fetching Liveness Result")
        val sessionResultRequest = SessionResultRequest()
        sessionResultRequest.authToken = auth_token
        SessionResultData.sessionResult(context, Constants.TOKEN, sessionId, sid, Constants.EMAIL, sessionResultRequest, object : ResultListener<Livenessdata> {
            override fun onSuccess(response: Livenessdata?) {
                postDataToServer(context, response, callback)
            }
            override fun onError(exception: String) {
                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                callback.invoke(exception)
            }
        })
    }

//    post scanned data to server
    @RequiresApi(Build.VERSION_CODES.O)
    private fun postDataToServer(
        context: Context,
        response: Livenessdata?,
        callback: (String) -> Unit
    ) {
        progressDialog?.setTitle("Posting data to server")
        val postdataRequest = DataSaveRequest()
        postdataRequest.email = Constants.EMAIL
        postdataRequest.randomid = sid
        val rawData = JsonParser().parse(rawResult).asJsonObject
        postdataRequest.regulaDat = rawData
        postdataRequest.livenessdata = (response as Livenessdata)

        PostAllData.postAllData(context, postdataRequest, object : ResultListener<DataSaveResponse> {
            override fun onSuccess(response: DataSaveResponse?) {
                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                callback.invoke(response?.message!!)
            }
            override fun onError(exception: String) {
                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                callback.invoke(exception)
            }
        })
    }

    //    Show scanner for document verification
    @RequiresApi(Build.VERSION_CODES.O)
    fun showScannerRequest(
        context: Context,
        bindingView: ViewGroup,
        custEmail: String,
        callback: (String) -> Unit
    ) {
        sid = getSid()
        DocumentReaderData.showScanner(context, sid, custEmail, auth_token) {
            progressDialog = showProgressDialog(context, "Initializing Face Scanner")
            faceSessionCreateRequest(context, bindingView) {

                callback.invoke(it)
            }
        }
    }

//    initialize database for scanning (needs to be initiliazed at least once before scanning)
    @RequiresApi(Build.VERSION_CODES.O)
    fun initializeDatabase(context: Context, message: (String) -> Unit){

        progressDialog = showProgressDialog(context,"Initializing")

        InitializeDatabase.InitDatabase(context, progressDialog) {
            if (it.equals("success")) {
                progressDialog?.setTitle("Configuring Face Scanner")
                configureFaceScanner(context, message)
            } else {
                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                message.invoke("Error : $it")
            }
        }
    }

//    Face scanner configuration
    @RequiresApi(Build.VERSION_CODES.O)
    private fun configureFaceScanner(context: Context, message: (String) -> Unit) {
        FaceScannerData.configureFaceScanner(context) {
            if (it.equals("FaceScannerConfigured")) {
                progressDialog?.setTitle("Authenticating")
                getAuthToken(context, message)
            } else {
                if (progressDialog?.isShowing!!) progressDialog?.dismiss()
                message.invoke("Error : $it")
            }
        }
    }

//    Request Auth Token
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAuthToken(context: Context, message: (String) -> Unit) {
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
    }

    //    returns a unique sId for every scan
    private fun getSid(): String {
        // Generate a random UUID
        val myUuid = UUID.randomUUID()
        val myUuidAsString = myUuid.toString()

        return myUuidAsString
    }

//    returns a list of available Processing Scenarios
    fun getScenariosList() {
        val list = arrayListOf(Scenarios.SCENARIO_FULL_PROCESS)
    }

}
