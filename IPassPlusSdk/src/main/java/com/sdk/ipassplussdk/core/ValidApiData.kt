package com.sdk.ipassplussdk.core


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.valid_api.ValidApiRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.valid_api.ValidApiResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ValidApiData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun validApiData(
        context: Context,
        token: String,
        request: ValidApiRequest,
        completion: ResultListener<ValidApiResponse>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .validApi(token, request).enqueue(object :
                    Callback<ValidApiResponse> {
                    override fun onResponse(
                        call: Call<ValidApiResponse>,
                        response: Response<ValidApiResponse>
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

                    override fun onFailure(call: Call<ValidApiResponse>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}