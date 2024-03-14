package com.app.ipassplus

import android.app.Activity
import android.os.Bundle
import com.sdk.ipassplussdk.verifylicense.VerifyLicense

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VerifyLicense.verifyLicense(this)

    }
}


