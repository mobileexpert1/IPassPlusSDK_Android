package com.sdk.ipassplussdk.core


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.ceon.GetCeon.GetCeonRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.ceon.GetCeon.GetCeonResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetCeonData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCeonData(
        context: Context,
        token: String,
        token1: String,
        request: GetCeonRequest,
        completion: ResultListener<BaseModel<GetCeonResponse>>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .getCeonData(token, token1, request).enqueue(object :
                    Callback<BaseModel<GetCeonResponse>> {
                    override fun onResponse(
                        call: Call<BaseModel<GetCeonResponse>>,
                        response: Response<BaseModel<GetCeonResponse>>
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

                    override fun onFailure(call: Call<BaseModel<GetCeonResponse>>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}