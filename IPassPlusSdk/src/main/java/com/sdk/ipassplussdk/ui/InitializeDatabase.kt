package com.sdk.ipassplussdk.ui

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.regula.documentreader.api.DocumentReader
import com.regula.documentreader.api.completions.IDocumentReaderPrepareCompletion
import com.regula.documentreader.api.errors.DocumentReaderException
import com.regula.documentreader.api.params.DocReaderConfig
import com.sdk.ipassplussdk.resultCallbacks.InitializeDatabaseCompletion
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.InternetConnectionService
import com.sdk.ipassplussdk.utils.LicenseUtil

object InitializeDatabase {

    @Transient
    var isInitializedByBleDevice: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    fun InitDatabase(context: Context, completion: InitializeDatabaseCompletion){
        if (InternetConnectionService.networkAvailable(context)) {
            if (LicenseUtil.readFileFromAssets("SdkLicense", "sdkLicense.license", context) == null
                && !isInitializedByBleDevice
            ) completion.onCompleted(false, "License not found")

            if (DocumentReader.Instance().isReady)
                onInitComplete(completion)

            val license = LicenseUtil.readFileFromAssets(
                "SdkLicense",
                "sdkLicense.license",
                context
            )

            license?.let {
                // Show progress indicator
//                initDialog = showProgressDialog(context, "Initializing")
                initializeReader(it, context, completion)
            }
        } else {
            completion.onCompleted(false, Constants.NO_INTERNET_TEXT)
        }
    }

    private fun initializeReader(
        license: ByteArray,
        context: Context, completion: InitializeDatabaseCompletion
    ) {
        DocumentReader.Instance().prepareDatabase(context, "FullAuth", object :
            IDocumentReaderPrepareCompletion {
            override fun onPrepareProgressChanged(progress: Int) {
                // getting progress
                completion.onProgressChanged(progress)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onPrepareCompleted(status: Boolean, error: DocumentReaderException?) {
                // database was prepared
                DocumentReader.Instance()
                    .initializeReader(context, DocReaderConfig(license)) {
                            success, error_initializeReader ->

                        DocumentReader.Instance().customization().edit().setShowHelpAnimation(false).apply()

                        if (success) {
                            onInitComplete(completion)
                        }
                        else {
                            completion.onCompleted(false, error_initializeReader?.message.toString())
                        }
                    }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onInitComplete(completion: InitializeDatabaseCompletion) {

        DocumentReader.Instance().processParams().debugSaveLogs = false
        DocumentReader.Instance().processParams().debugSaveCroppedImages = false
        DocumentReader.Instance().processParams().debugSaveImages = false

        val path = DocumentReader.Instance().processParams().sessionLogFolder
        Log.d("SdkLicense" , "Path: $path")


        DocumentReader.Instance().setLocalizationCallback { stringId ->
            if(stringId == "strLookingDocument")
            // return@setLocalizationCallback SettingsActivity.customString
                return@setLocalizationCallback ""
            return@setLocalizationCallback null
        }
        completion.onCompleted(true,"Success")

//        FaceScannerData.configureFaceScanner(context, callback)

    }



}