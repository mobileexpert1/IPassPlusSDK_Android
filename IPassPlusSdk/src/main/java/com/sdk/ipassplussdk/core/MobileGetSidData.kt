package com.sdk.ipassplussdk.core


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.ipass.ipass_data_get_sid.IpassDataGetSidResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MobileGetSidData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun mobileGetSidData(
        context: Context,
        token: String,
        token1: String,
        sid: String,
        completion: ResultListener<BaseModel<IpassDataGetSidResponse>>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .dataGetSid(token, token1, sid).enqueue(object :
                    Callback<BaseModel<IpassDataGetSidResponse>> {
                    override fun onResponse(
                        call: Call<BaseModel<IpassDataGetSidResponse>>,
                        response: Response<BaseModel<IpassDataGetSidResponse>>
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

                    override fun onFailure(call: Call<BaseModel<IpassDataGetSidResponse>>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}