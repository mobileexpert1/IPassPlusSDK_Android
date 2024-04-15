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
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.core.FaceSimilarityData
import com.sdk.ipassplussdk.core.LoginData
import com.sdk.ipassplussdk.model.request.facesimilarity.FaceSimilarityRequest
import com.sdk.ipassplussdk.model.request.login.LoginRequest
import com.sdk.ipassplussdk.model.response.facesimilarity.FaceSimilarityResponse
import com.sdk.ipassplussdk.model.response.login.LoginResponse
import com.sdk.ipassplussdk.utils.Constants

object AwsFaceRekognition {

    @RequiresApi(Build.VERSION_CODES.O)
    fun configureAws(context: Context, callback: (String) -> Unit) {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(context)
            callback.invoke("awsConfigured")
//            val request = LoginRequest(Constants.EMAIL, Constants.PASSWORD)

//            LoginData.login(context, request, completion)
        } catch (e: AmplifyException) {
            Log.e("AmplifyException", e.message.toString())
            callback.invoke(e.message.toString())
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
                            Log.i("MyApp", "Face Liveness flow is complete")
                            bindView.removeViewInLayout(this)
                            callback.invoke("success")
                           /* val request = FaceSimilarityRequest()
                            request.sid = "183"
                            request.email = "ipassmobile@yopmail.com"
                            request.targetImageBase64 = "ipassmobile@yopmail.com"
                            request.sourceImageBase64 = "ipassmobile@yopmail.com"
                            request.authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjYxNTQyNmMzY2RmZDA4NDlmYTQ1NzcxIiwiZW1haWwiOiJpcGFzc21vYmlsZUB5b3BtYWlsLmNvbSIsImlhdCI6MTcxMjgzMzE3NCwiZXhwIjoxNzEyODM0OTc0fQ.7mmV3gcHd54YQ8uohfKwI_RJW0GzIYH_SFG--VGZ_Zg"
                            FaceSimilarityData.facesimilarity(context, Constants.TOKEN, r, object : ResultListener<FaceSimilarityResponse> {
                                override fun onSuccess(response: FaceSimilarityResponse?) {
                                    TODO("Not yet implemented")
                                }

                                override fun onError(exception: String) {
                                    TODO("Not yet implemented")
                                }

                            })*/
                            // displayResults(results)
                            // The Face Liveness flow is complete and the session
                            // results are ready. Use your backend to retrieve the
                            // results for the Face Liveness session.
                        },
                        onError = { error ->
                            Toast.makeText(context, error.throwable?.message.toString(), Toast.LENGTH_SHORT).show()
                            Log.e("MyApp", "Error during Face Liveness flow", error.throwable)
                            Log.e("MyApp", "Error during Face Liveness flow ${error.message}")
                            Log.e("MyApp", "Error during Face Liveness flow ${error.recoverySuggestion}")
                            callback.invoke("Error during Face Liveness flow ${error.message}")
                            // findNavController().popBackStack()
                            // An error occurred during the Face Liveness flow, such as
                            // time out or missing the required permissions.
                        }
                    )
                }
            }
        }

//        bindparentViewGroup.removeAllViews() // Remove all existing views from parentViewGroup
        bindView.addView(composeView) // Add ComposeView to parentViewGroup
    }
}
