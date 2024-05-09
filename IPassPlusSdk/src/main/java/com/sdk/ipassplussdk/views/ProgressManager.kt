package com.sdk.ipassplussdk.views

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sdk.ipassplussdk.R


object ProgressManager {
    private var progressDialog: ProgressDialog? = null
    private var dialogQQ: AlertDialog? = null

    fun showProgress(context: Context) {
        dismissProgress()



//        progressDialog = ProgressDialog(context)
//        progressDialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
//        progressDialog?.getWindow()?.setGravity(Gravity.DISPLAY_CLIP_VERTICAL);
//
//        val window: Window = progressDialog?.getWindow()!!
//        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
//        window.setGravity(Gravity.CENTER)
//
////        progressDialog?.setContentView(v)
//        progressDialog?.setCancelable(false)
//        progressDialog?.show()


        val dialog = MaterialAlertDialogBuilder(context)
        dialog.background = ResourcesCompat.getDrawable(context.resources, R.drawable.rounded, context.theme)
        // Inflate a custom layout for the dialog
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.simple_dialog, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialogQQ = dialog.show()

    }


        fun showProgressDialog(context: Context, msg: String) {
        val dialog = MaterialAlertDialogBuilder(context)
//        dialog?.background = ResourcesCompat.getDrawable(context.resources, R.drawable.rounded, context.theme)
        dialog?.setTitle(msg)

        // Inflate a custom layout for the dialog
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.simple_dialog, null)
        dialog?.setView(dialogView)

        dialog?.setCancelable(false)

            dialogQQ =    dialog?.show()
    }

    fun dismissProgress() {
        dialogQQ?.dismiss()
        dialogQQ = null
    }

}