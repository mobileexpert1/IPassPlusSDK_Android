package com.sdk.ipassplussdk.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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

@SuppressLint("StaticFieldLeak")
object DocumentReaderData {
    private var context: Context? = null
    private var rawResult: String? = null
    private var callback: (Boolean, String) -> Unit = { _, _ ->}

    @RequiresApi(Build.VERSION_CODES.O)
    fun showScanner(context: Context,
                    callback: (Boolean, String) -> Unit
                    ) {
        this.context = context
        this.callback = callback

        DocumentReader.Instance().processParams().multipageProcessing = true
        DocumentReader.Instance().functionality().edit().setShowSkipNextPageButton(false).apply()

        val scannerConfig = ScannerConfig.Builder(Scenario.SCENARIO_FULL_PROCESS).build()
        DocumentReader.Instance().showScanner(context, scannerConfig, completion)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val completion = IDocumentReaderCompletion { action, results, error ->
        DocumentReader.Instance().customization().edit().setUiCustomizationLayer(null).apply()
        if (action == DocReaderAction.COMPLETE
            || action == DocReaderAction.TIMEOUT) {

            if (results?.chipPage != 0) {
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
                            callback.invoke(true, rawResult!!)
                        } else if (rfidAction == DocReaderAction.CANCEL) {
                            rawResult = results?.rawResult
                            callback.invoke(true, rawResult!!)
                        }
                    }
                })
            } else {
                rawResult = results.rawResult
                callback.invoke(true, rawResult!!)
            }

        }
        else if (action == DocReaderAction.CANCEL) {
                if (DocumentReader.Instance().functionality().isManualMultipageMode)
                    DocumentReader.Instance().functionality().edit().setManualMultipageMode(false).apply()
                callback.invoke(false, "Scanning Cancelled")
            }
            else if (action == DocReaderAction.ERROR) {
                callback.invoke(false, error?.message!!)
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


}