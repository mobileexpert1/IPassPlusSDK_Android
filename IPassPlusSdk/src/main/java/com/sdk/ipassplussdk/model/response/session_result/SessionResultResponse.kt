package com.sdk.ipassplussdk.model.response.session_result

import com.google.gson.annotations.SerializedName

data class SessionResultResponse (

  @SerializedName("_id"      ) var Id       : String?   = null,
  @SerializedName("__v"      ) var _v       : Int?      = null,
  @SerializedName("email"    ) var email    : String?   = null,
  @SerializedName("response" ) var response : Response? = Response(),
  @SerializedName("sid"      ) var sid      : String?   = null

)


data class Response (

  @SerializedName("\$metadata"      ) var metadata      : metadata?        = metadata(),
@SerializedName("AuditImages"    ) var AuditImages    : ArrayList<String> = arrayListOf(),
@SerializedName("Confidence"     ) var Confidence     : Double?           = null,
@SerializedName("ReferenceImage" ) var ReferenceImage : ReferenceImage?   = ReferenceImage(),
@SerializedName("SessionId"      ) var SessionId      : String?           = null,
@SerializedName("Status"         ) var Status         : String?           = null

)


data class metadata (

  @SerializedName("httpStatusCode"  ) var httpStatusCode  : Int?    = null,
  @SerializedName("requestId"       ) var requestId       : String? = null,
  @SerializedName("attempts"        ) var attempts        : Int?    = null,
  @SerializedName("totalRetryDelay" ) var totalRetryDelay : Int?    = null

)


data class ReferenceImage (

  @SerializedName("BoundingBox" ) var BoundingBox : BoundingBox? = BoundingBox(),
  @SerializedName("Bytes"       ) var Bytes       : String?      = null

)



data class BoundingBox (

  @SerializedName("Height" ) var Height : Double? = null,
  @SerializedName("Left"   ) var Left   : Double? = null,
  @SerializedName("Top"    ) var Top    : Double? = null,
  @SerializedName("Width"  ) var Width  : Double? = null

)