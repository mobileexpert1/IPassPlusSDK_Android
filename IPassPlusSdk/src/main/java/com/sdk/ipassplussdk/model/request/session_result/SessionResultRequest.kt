package com.sdk.ipassplussdk.model.request.session_result

import com.google.gson.annotations.SerializedName

data class SessionResultRequest (

  @SerializedName("auth_token" ) var authToken : String? = null

)