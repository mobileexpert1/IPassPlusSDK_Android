package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.aml_manual.AmlManualRequest
import com.sdk.ipassplussdk.model.request.check_face_analysis.CheckFaceAnalysisRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.aml_manual.AmlManualResponse
import com.sdk.ipassplussdk.model.response.check_face_analysis.CheckFaceAnalysisResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AmlManualData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun amlManual(
        context: Context,
        token: String,
        request: AmlManualRequest,
        completion: ResultListener<AmlManualResponse>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .amlManual(token, request).enqueue(object :
                    Callback<AmlManualResponse> {
                    override fun onResponse(
                        call: Call<AmlManualResponse>,
                        response: Response<AmlManualResponse>
                    ) {
                        print("Response ==> $response")
                        if (response.isSuccessful) {
                            completion.onSuccess(response.body()!!)
                        } else {
                            try {
                                completion.onError(ErrorHandler(response,"user"))
                            }catch (e: Exception){

                            }
                        }
                    }

                    override fun onFailure(call: Call<AmlManualResponse>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }
}