package com.sdk.ipassplussdk.views

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.sdk.ipassplussdk.R


object ProgressManager {
    private var dialogQQ: Dialog? = null

    fun showProgress(context: Context) {
        dismissProgress()

        dialogQQ = Dialog(context)
        dialogQQ?.setContentView(R.layout.loading)
        dialogQQ?.setCancelable(false)
        dialogQQ?.show()
        dialogQQ?.findViewById<ImageView>(R.id.loading_icon)?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate360))

    }


    fun dismissProgress() {
        dialogQQ?.dismiss()
        dialogQQ = null
    }

}