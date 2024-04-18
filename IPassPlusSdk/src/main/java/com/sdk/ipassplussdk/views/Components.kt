package com.sdk.ipassplussdk.views

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.sdk.ipassplussdk.R

object Components {
    fun showDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage("license in assets is missed")
            .setPositiveButton("Yes"
            ) { _, _ ->
              //  finish()
            }
            .setCancelable(false)
            .show()
    }

//    fun showProgressDialog(context: Context, msg: String): AlertDialog {
//        val dialog = MaterialAlertDialogBuilder(context, R.style.Theme_MyApp_Dialog_Alert)
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
}