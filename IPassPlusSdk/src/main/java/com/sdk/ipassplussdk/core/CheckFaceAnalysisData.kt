package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.check_face_analysis.CheckFaceAnalysisRequest
import com.sdk.ipassplussdk.model.request.login.LoginRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.check_face_analysis.CheckFaceAnalysisResponse
import com.sdk.ipassplussdk.model.response.login.LoginResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CheckFaceAnalysisData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun checkFaceAnalysis(
        context: Context,
        token: String,
        token1: String,
        request: CheckFaceAnalysisRequest,
        completion: ResultListener<BaseModel<CheckFaceAnalysisResponse>>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .checkFaceAnaliseaws(token,token1,request).enqueue(object :
                    Callback<BaseModel<CheckFaceAnalysisResponse>> {
                    override fun onResponse(
                        call: Call<BaseModel<CheckFaceAnalysisResponse>>,
                        response: Response<BaseModel<CheckFaceAnalysisResponse>>
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

                    override fun onFailure(call: Call<BaseModel<CheckFaceAnalysisResponse>>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}