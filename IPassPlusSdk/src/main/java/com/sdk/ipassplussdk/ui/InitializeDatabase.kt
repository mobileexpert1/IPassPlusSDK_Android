package com.sdk.ipassplussdk.ui

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.regula.documentreader.api.DocumentReader
import com.regula.documentreader.api.completions.IDocumentReaderPrepareCompletion
import com.regula.documentreader.api.errors.DocumentReaderException
import com.regula.documentreader.api.params.DocReaderConfig
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.InternetConnectionService
import com.sdk.ipassplussdk.utils.LicenseUtil
import com.sdk.ipassplussdk.views.Components.showProgressDialog
import com.sdk.ipassplussdk.views.Components.showDialog

object InitializeDatabase {

    @Transient
//    private var initDialog: AlertDialog? = null
    var isInitializedByBleDevice: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    fun InitDatabase(context: Context, initDialog: AlertDialog?, callback: (String) -> Unit){
        if (InternetConnectionService.networkAvailable(context)) {
            if (LicenseUtil.readFileFromAssets("Regula", "regula.license", context) == null
                && !isInitializedByBleDevice
            ) showDialog(
                context
            )

            if (DocumentReader.Instance().isReady)
                onInitComplete(context, callback)

            val license = LicenseUtil.readFileFromAssets(
                "Regula",
                "regula.license",
                context
            )

            license?.let {
                // Show progress indicator
//                initDialog = showProgressDialog(context, "Initializing")
                initializeReader(it, context,initDialog, callback)
            }
        } else {
            callback.invoke(Constants.NO_INTERNET_TEXT)
        }
    }

//    fun showDialog(context: Context, msg: String): AlertDialog {
//        val dialog = MaterialAlertDialogBuilder(context)
//        dialog.background = ResourcesCompat.getDrawable(context.resources, R.drawable.rounded, context.theme)
//        dialog.setTitle(msg)
//
//        // Inflate a custom layout for the dialog
//        val inflater = LayoutInflater.from(context)
//        val dialogView = inflater.inflate(R.layout.simple_dialog, null)
//        dialog.setView(dialogView)
//
//        dialog.setCancelable(false)
//
//        return dialog.show()
//    }

    private fun initializeReader(license: ByteArray,context: Context, initDialog: AlertDialog?, callback: (String) -> Unit) {
        DocumentReader.Instance().prepareDatabase(context, "FullAuth", object :
            IDocumentReaderPrepareCompletion {
            override fun onPrepareProgressChanged(progress: Int) {
                // getting progress
                initDialog!!.setTitle("Downloading database: $progress%")
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onPrepareCompleted(status: Boolean, error: DocumentReaderException?) {
                // database was prepared
                DocumentReader.Instance()
                    .initializeReader(context, DocReaderConfig(license)) {
                            success, error_initializeReader ->

                        DocumentReader.Instance().customization().edit().setShowHelpAnimation(false).apply()

                        if (success) {
                            onInitComplete(context, callback)
                        }
                        else {
                            Toast.makeText(
                                context,
                                "Init failed:$error_initializeReader",
                                Toast.LENGTH_LONG
                            ).show()

                            Log.e("tag", "error..." + error_initializeReader)
                            if (initDialog!!.isShowing) initDialog!!.dismiss()
                        }
                    }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onInitComplete(context: Context, callback: (String) -> Unit) {

        DocumentReader.Instance().processParams().debugSaveLogs = false
        DocumentReader.Instance().processParams().debugSaveCroppedImages = false
        DocumentReader.Instance().processParams().debugSaveImages = false

        val path = DocumentReader.Instance().processParams().sessionLogFolder
        Log.d("Regula" , "Path: $path")


        DocumentReader.Instance().setLocalizationCallback { stringId ->
            if(stringId == "strLookingDocument")
            // return@setLocalizationCallback SettingsActivity.customString
                return@setLocalizationCallback ""
            return@setLocalizationCallback null
        }
        callback.invoke("success")

//        AwsFaceRekognition.configureAws(context, callback)

    }



}