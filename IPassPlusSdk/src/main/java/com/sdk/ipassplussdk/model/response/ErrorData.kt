package com.ipasssdk.model.response

import com.google.gson.annotations.SerializedName
import com.sdk.ipassplussdk.model.response.ErrorCodeResponse

class ErrorData {
    @SerializedName("error")
    var errorCodeResponse : ErrorCodeResponse? = null

}