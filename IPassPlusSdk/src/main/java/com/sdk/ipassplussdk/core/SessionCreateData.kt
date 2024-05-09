package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.request.session_create.SessionCreateRequest
import com.sdk.ipassplussdk.model.response.session_create.Data
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SessionCreateData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun sessionCreate(
        context: Context,
        token: String,
        request: SessionCreateRequest,
        completion: ResultListener<Data>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .sessionCreate(token, request).enqueue(object :
                    Callback<Data> {
                    override fun onResponse(
                        call: Call<Data>,
                        response: Response<Data>
                    ) {
                        if (response.isSuccessful) {
                            completion.onSuccess(response.body()!!)
                        } else {
                            try {
                                completion.onError(response.message())
                            }catch (e: Exception){
                                completion.onError("")
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