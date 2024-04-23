package com.sdk.ipassplussdk.core

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.sdk.ipassplussdk.apis.ApiClient
import com.sdk.ipassplussdk.apis.ApiInterface
import com.sdk.ipassplussdk.apis.ResultListener
import com.sdk.ipassplussdk.model.response.document_scanner.DocumentScannerResponse
import com.sdk.ipassplussdk.model.response.liveness_facesimilarity.FaceScannerResponse
import com.sdk.ipassplussdk.utils.Constants
import com.sdk.ipassplussdk.utils.ErrorHandler
import com.sdk.ipassplussdk.utils.InternetConnectionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GetResults {

    @RequiresApi(Build.VERSION_CODES.O)
    fun FaceScannerResult(
        context: Context,
        token: String,
        sessionId: String,
        completion: ResultListener<FaceScannerResponse>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .getLivenessFaceSimilarityData(token, sessionId).enqueue(object :
                    Callback<FaceScannerResponse> {
                    override fun onResponse(
                        call: Call<FaceScannerResponse>,
                        response: Response<FaceScannerResponse>
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

                    override fun onFailure(call: Call<FaceScannerResponse>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun DocumentScanerResult(
        context: Context,
        token: String,
        sessionId: String,
        completion: ResultListener<DocumentScannerResponse>
    ) {
        if (InternetConnectionService.networkAvailable(context)) {
            ApiClient("")?.create(ApiInterface::class.java)!!
                .getDocumentScannerData(token, sessionId).enqueue(object :
                    Callback<DocumentScannerResponse> {
                    override fun onResponse(
                        call: Call<DocumentScannerResponse>,
                        response: Response<DocumentScannerResponse>
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

                    override fun onFailure(call: Call<DocumentScannerResponse>, t: Throwable) {
                        completion.onError(t.message.toString())
                    }
                })
        } else {
            completion.onError(Constants.NO_INTERNET_TEXT)
        }

    }
}