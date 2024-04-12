package com.sdk.ipassplussdk.core


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.regula.regula_get_data_by_email.OcrGetDataByEmailRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.regula.regula_get_data_by_email.OcrGetDataByEmailResponse
import com.sdk.ipassplussdk.model.response.regula.regula_post_data.OcrPostDataResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object OcrGetDataByEmail {
    @RequiresApi(Build.VERSION_CODES.O)
    fun ocrGetDataByEmail(
        context: Context,
        token: String,
        token1: String,
        request: OcrGetDataByEmailRequest,
        completion: ResultListener<BaseModel<OcrGetDataByEmailResponse>>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .ocrGetDataBy(token, token1, request).enqueue(object :
                    Callback<BaseModel<OcrGetDataByEmailResponse>> {
                    override fun onResponse(
                        call: Call<BaseModel<OcrGetDataByEmailResponse>>,
                        response: Response<BaseModel<OcrGetDataByEmailResponse>>
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

                    override fun onFailure(call: Call<BaseModel<OcrGetDataByEmailResponse>>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}