package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.livenesscheck.LivenessCheckRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.livenesscheck.LivenessCheckResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
object LivenessCheckData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun livenessCheck(
        context: Context,
        token: String,
        sessionId: String,
        sid: String,
        email: String,
        request: LivenessCheckRequest,
        completion: ResultListener<LivenessCheckResponse>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .livenessCheck(token, sessionId, sid, email, request).enqueue(object :
                    Callback<LivenessCheckResponse> {
                    override fun onResponse(
                        call: Call<LivenessCheckResponse>,
                        response: Response<LivenessCheckResponse>
                    ) {
                        print("Response ==> $response")
                        if (response.isSuccessful) {
                            completion.onSuccess(response.body()!!)
                        } else {
                            try {
                                completion.onError(ErrorHandler(response,"user"))
                            }catch (e:Exception){

                            }
                        }
                    }
                    override fun onFailure(call: Call<LivenessCheckResponse>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}