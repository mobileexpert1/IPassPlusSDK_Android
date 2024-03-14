package com.sdk.ipassplussdk.verifylicense

import android.content.Context
import com.sdk.ipassplussdk.utils.LicenseUtil
import com.sdk.ipassplussdk.views.Components

object VerifyLicense {

fun verifyLicense(context: Context){
    if (LicenseUtil.readFileFromAssets("Regula", "regula.license", context) == null)
        Components.showDialog(context)
}

}