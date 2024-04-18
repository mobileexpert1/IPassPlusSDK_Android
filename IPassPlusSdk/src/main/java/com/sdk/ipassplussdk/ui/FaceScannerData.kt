package com.sdk.ipassplussdk.ui

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.liveness.ui.FaceLivenessDetector
import com.amplifyframework.ui.liveness.ui.LivenessColorScheme

object FaceScannerData {

    @RequiresApi(Build.VERSION_CODES.O)
    fun configureFaceScanner(context: Context, callback: (String) -> Unit) {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(context)
            callback.invoke("FaceScannerConfigured")
        } catch (e: AmplifyException) {
            callback.invoke("Error Initializing Face Scanner")
        }
    }
    fun initFaceDetector(context: Context, sessionId: String, bindView:ViewGroup, callback: (String) -> Unit) {

        val composeView = ComposeView(context).apply {
            setContent {
                MaterialTheme(
                    colorScheme = LivenessColorScheme.default()
                ) {
                    FaceLivenessDetector(
                        sessionId = sessionId,
                        region = "us-east-1",
                        disableStartView = true,
                        onComplete = {
                                     Log.e("FaceLivenessDetector", "onComplete")
                            bindView.removeView(this as ComposeView)
//                            bindView.removeView(this.rootView)
                            bindView.removeViewInLayout(this.rootView)
                            callback.invoke("success")
                        },
                        onError = { error ->
                            Log.e("FaceLivenessDetector", "onError")
                            callback.invoke("Error during Face Liveness flow")
                        }
                    )
                }
            }
        }

        bindView.addView(composeView) // Add ComposeView to parentViewGroup
    }
}
