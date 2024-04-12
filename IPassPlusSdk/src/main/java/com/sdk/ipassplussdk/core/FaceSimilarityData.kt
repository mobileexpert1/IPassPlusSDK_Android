package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.facesimilarity.FaceSimilarityRequest
import com.sdk.ipassplussdk.model.response.facesimilarity.Data
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object FaceSimilarityData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun facesimilarity(
        context: Context,
        token: String,
        request: FaceSimilarityRequest,
        completion: ResultListener<Data>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .facesimilarity(token, request).enqueue(object :
                    Callback<Data> {
                    override fun onResponse(
                        call: Call<Data>,
                        response: Response<Data>
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

                    override fun onFailure(call: Call<Data>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }
}