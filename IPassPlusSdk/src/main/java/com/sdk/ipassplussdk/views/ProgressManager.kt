package com.sdk.ipassplussdk.views

import android.app.ProgressDialog
import android.content.Context

object ProgressManager {
    private var progressDialog: ProgressDialog? = null

    fun showProgress(context: Context, message: String) {
        dismissProgress()
        progressDialog = ProgressDialog(context)
        progressDialog?.setMessage(message)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    fun dismissProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}