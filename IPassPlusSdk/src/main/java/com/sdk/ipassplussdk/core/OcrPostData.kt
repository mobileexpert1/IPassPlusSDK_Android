package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.ipass.ipass_post_data.OcrPostdataRequest
import com.sdk.ipassplussdk.model.response.ipass.ipass_post_data.OcrPostDataResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object OcrPostData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun ocrPostData(
        context: Context,
        token: String,
        request: OcrPostdataRequest,
        completion: ResultListener<OcrPostDataResponse>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .ocrPostData(token, request).enqueue(object :
                    Callback<OcrPostDataResponse> {
                    override fun onResponse(
                        call: Call<OcrPostDataResponse>,
                        response: Response<OcrPostDataResponse>
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

                    override fun onFailure(call: Call<OcrPostDataResponse>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}