package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.response.data_save.DataSaveRequest
import com.sdk.ipassplussdk.model.response.data_save.DataSaveResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostAllData {
    @RequiresApi(Build.VERSION_CODES.O)
    fun postAllData(
        context: Context,
        request: DataSaveRequest,
        completion: ResultListener<DataSaveResponse>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .dataSaveRequest( request ).enqueue(object :
                    Callback<DataSaveResponse> {
                    override fun onResponse(
                        call: Call<DataSaveResponse>,
                        response: Response<DataSaveResponse>
                    ) {
                        print("Response ==> $response")
                        if (response.isSuccessful) {
                            completion.onSuccess(response.body()!!)
                        } else {
                            try {
                                completion.onError(response.message())
                            }catch (e:Exception){
                                completion.onError("")
                            }
                        }
                    }

                    override fun onFailure(call: Call<DataSaveResponse>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }

}