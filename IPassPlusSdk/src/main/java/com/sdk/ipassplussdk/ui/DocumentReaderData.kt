package com.sdk.ipassplussdk.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.regula.documentreader.api.DocumentReader
import com.regula.documentreader.api.completions.IDocumentReaderCompletion
import com.regula.documentreader.api.completions.rfid.IRfidReaderCompletion
import com.regula.documentreader.api.config.ScannerConfig
import com.regula.documentreader.api.enums.DocReaderAction
import com.regula.documentreader.api.enums.Scenario
import com.regula.documentreader.api.enums.eRFID_DataFile_Type
import com.regula.documentreader.api.enums.eRFID_NotificationCodes
import com.regula.documentreader.api.errors.DocReaderRfidException
import com.regula.documentreader.api.errors.DocumentReaderException
import com.regula.documentreader.api.results.DocumentReaderNotification
import com.regula.documentreader.api.results.DocumentReaderResults
import com.sdk.ipassplussdk.core.IPassSDK.setRawResult
import com.sdk.ipassplussdk.model.response.regula_data.RegulaData
import org.json.JSONObject

@SuppressLint("StaticFieldLeak")
object DocumentReaderData {

    var isAnimationStarted: Boolean = false
    var mTimerAnimator: ValueAnimator? = null
    var loadingDialog: AlertDialog? = null
    lateinit var documentReader: DocumentReaderResults
    private var context: Context? = null
    private var sid: String? = null
    private var email: String? = null
    private var auth_token: String? = null
    private var rawResult: String? = null
    private var callback: (String) -> Unit = {}
//    private var img1: String? = null
//    private var img2: String? = null

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun showScanner(context: Context) {
//        this.context = context
//        val scannerConfig = ScannerConfig.Builder(Scenario.SCENARIO_FULL_AUTH).build()
//        DocumentReader.Instance().showScanner(context, scannerConfig, completion)
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showScanner(context: Context,
                    sid:String,
                    custEmail: String,
                    auth_token: String,
                    callback: (String) -> Unit
//                    img1: String,
//                    img2: String,
                    ) {
        this.context = context
        this.sid = sid
        this.email = custEmail
        this.auth_token = auth_token
        this.callback = callback
//        this.img1 = img1
//        this.img2 = img2

        DocumentReader.Instance().processParams().multipageProcessing = true
        DocumentReader.Instance().functionality().edit().setShowSkipNextPageButton(false).apply()

        val scannerConfig = ScannerConfig.Builder(Scenario.SCENARIO_FULL_PROCESS).build()
        DocumentReader.Instance().showScanner(context, scannerConfig, completion)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val completion = IDocumentReaderCompletion { action, results, error ->

        if(!isAnimationStarted) {
            mTimerAnimator?.let {
                it.start()
                isAnimationStarted = true
            }
        }

        if (action == DocReaderAction.COMPLETE
            || action == DocReaderAction.TIMEOUT) {

            rawResult = results?.rawResult!!
            setRawResult(rawResult!!)
            callback.invoke("success")

//            ProgressManager.showProgress(context!!, "Uploading scanned data")

            if (results.chipPage != 0) {
                DocumentReader.Instance().startRFIDReader(context!!, object: IRfidReaderCompletion() {
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
                        if (rfidAction == DocReaderAction.COMPLETE) {
                            rawResult = results_RFIDReader?.rawResult!!
                            setRawResult(rawResult!!)
                            callback.invoke("success")
                        } else if (rfidAction == DocReaderAction.CANCEL) {
                            rawResult = results.rawResult
                            setRawResult(rawResult!!)
                            callback.invoke("success")
                        }
                    }
                })
            } else {
                rawResult = results.rawResult!!
                setRawResult(rawResult!!)
                callback.invoke("success")
            }


//            val request = OcrPostdataRequest()
//            request.sid = sid
//            request.email = Constants.EMAIL
//            request.custEmail = email
//            request.workflow = "10032"
//            request.authToken = auth_token
//            results?.graphicResult?.fields?.forEach {
//                if (it.getFieldName(context!!).equals("Document image")) {
//                    println(it.getFieldName(context!!))
//                    if (request.image1.isNullOrEmpty()) {
//                        request.image1 = it.imageBase64()
//                    } else {
//                        request.image2 = it.imageBase64()
//                    }
//                }
//            }
//
//            OcrPostData.ocrPostData(context!!, Constants.TOKEN, request, object : ResultListener<OcrPostDataResponse> {
//                override fun onSuccess(response: OcrPostDataResponse?) {
//                    ProgressManager.dismissProgress()
//                    print(response)
//                    Log.e("call","Ocr data Response:: "+response.toString())
//                    Toast.makeText(context, "Success ::  "+response.toString(), Toast.LENGTH_LONG).show()
//                }
//                override fun onError(exception: String) {
//                    ProgressManager.dismissProgress()
//                    print(exception)
//                    Toast.makeText(context, exception, Toast.LENGTH_LONG).show()
//                }
//            })

//            OcrPostData.ocrPostData(context!!, "token", OcrPostdataRequest(), completion)


            hideDialog()
            cancelAnimation()
        }
        else
            if (action == DocReaderAction.CANCEL) {
                if (DocumentReader.Instance().functionality().isManualMultipageMode)
                    DocumentReader.Instance().functionality().edit().setManualMultipageMode(false).apply()

                hideDialog()
                cancelAnimation()
            }
            else if (action == DocReaderAction.ERROR) {
                hideDialog()
                cancelAnimation()
            }
    }

    @SuppressLint("WrongConstant")
    fun rfidProgress(code: Int, value: Int) {
        val hiword = code and -0x10000
        val loword = code and 0x0000FFFF
        when (hiword) {
            eRFID_NotificationCodes.RFID_NOTIFICATION_PCSC_READING_DATAGROUP -> if (value == 0) {
                Log.d("Rfid", "Current group: " + String.format(
                    context?.getString(com.regula.documentreader.api.R.string.strReadingRFIDDG)!!,
                    eRFID_DataFile_Type.getTranslation(
                        context, loword
                    )))
            }
        }
    }

    private fun hideDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
    private fun cancelAnimation() {
        mTimerAnimator?.let {
            it.cancel()
            isAnimationStarted = false
            mTimerAnimator = null
        }
        DocumentReader.Instance().customization().edit().setUiCustomizationLayer(null).apply()
    }

}