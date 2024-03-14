package com.sdk.ipassplussdk.views

import android.content.Context
import androidx.appcompat.app.AlertDialog

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
}