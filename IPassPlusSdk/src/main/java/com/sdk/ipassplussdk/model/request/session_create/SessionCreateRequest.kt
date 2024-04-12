package com.sdk.ipassplussdk.model.request.session_create

import com.google.gson.annotations.SerializedName

data class SessionCreateRequest (

  @SerializedName("auth_token" ) var authToken : String? = null,
  @SerializedName("email"      ) var email     : String? = null

)