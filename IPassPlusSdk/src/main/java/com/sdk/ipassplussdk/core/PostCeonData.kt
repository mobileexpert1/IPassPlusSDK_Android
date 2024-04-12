package com.sdk.ipassplussdk.core


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.ceon.PostCeon.PostCeonRequest
import com.sdk.ipassplussdk.model.response.BaseModel
import com.sdk.ipassplussdk.model.response.ceon.PostCeon.PostCeonResponse
import com.sdk.ipassplussdk.model.response.regula.regula_post_data.OcrPostDataResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostCeonData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun postCeonData(
        context: Context,
        token: String,
        token1: String,
        request: PostCeonRequest,
        completion: ResultListener<BaseModel<PostCeonResponse>>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .postCeonData(token, token1, request).enqueue(object :
                    Callback<BaseModel<PostCeonResponse>> {
                    override fun onResponse(
                        call: Call<BaseModel<PostCeonResponse>>,
                        response: Response<BaseModel<PostCeonResponse>>
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

                    override fun onFailure(call: Call<BaseModel<PostCeonResponse>>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}