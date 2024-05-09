package com.sdk.ipassplussdk.views

import android.app.ProgressDialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog


object ProgressManager {
    private var progressDialog: ProgressDialog? = null
    private var dialogQQ: AlertDialog? = null

    fun showProgress(context: Context) {
        dismissProgress()
        progressDialog = ProgressDialog(context)
        progressDialog?.getWindow()?.setGravity(Gravity.CENTER);
       /* val window: Window = progressDialog?.getWindow()!!
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        progressDialog!!.addContentView(
            ProgressBar(context),
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        )*/
        progressDialog?.setCancelable(false)
        progressDialog?.show()

    }


    fun dismissProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

}