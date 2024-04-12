package com.sdk.ipassplussdk.model.response.session_result

import com.google.gson.annotations.SerializedName

data class SessionResultResponse (

  @SerializedName("_id"      ) var Id       : String?   = null,
  @SerializedName("response" ) var response : Response? = Response(),
  @SerializedName("sid"      ) var sid      : String?   = null,
  @SerializedName("email"    ) var email    : String?   = null,
  @SerializedName("__v"      ) var _v       : Int?      = null

)


data class Response (

  @SerializedName("metadata" ) var metadata : metadata? = metadata(),
@SerializedName("SessionId" ) var SessionId : String?    = null,
@SerializedName("Status"    ) var Status    : String?    = null

)


data class metadata (

  @SerializedName("httpStatusCode"  ) var httpStatusCode  : Int?    = null,
  @SerializedName("requestId"       ) var requestId       : String? = null,
  @SerializedName("attempts"        ) var attempts        : Int?    = null,
  @SerializedName("totalRetryDelay" ) var totalRetryDelay : Int?    = null

)