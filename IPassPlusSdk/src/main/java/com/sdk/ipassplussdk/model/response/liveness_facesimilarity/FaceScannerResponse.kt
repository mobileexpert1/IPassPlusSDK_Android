package com.sdk.ipassplussdk.model.response.liveness_facesimilarity

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class FaceScannerResponse (

  @SerializedName("status"  ) var status  : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : JsonObject?    = null

)


data class Data (

  @SerializedName("livenessResult"       ) var livenessResult       : ArrayList<LivenessResult>       = arrayListOf(),
  @SerializedName("faceComparisonResult" ) var faceComparisonResult : ArrayList<FaceComparisonResult> = arrayListOf()

)

data class FaceComparisonResult (

  @SerializedName("_id"               ) var Id                : String? = null,
  @SerializedName("sourceImageBase64" ) var sourceImageBase64 : String? = null,
  @SerializedName("targetImageBase64" ) var targetImageBase64 : String? = null,
  @SerializedName("facePercentage"    ) var facePercentage    : Double?    = null,
  @SerializedName("timestamp"         ) var timestamp         : String? = null,
  @SerializedName("email"             ) var email             : String? = null,
  @SerializedName("sid"               ) var sid               : String? = null,
  @SerializedName("status"            ) var status            : String? = null,
  @SerializedName("confidence"        ) var confidence        : String? = null,
  @SerializedName("__v"               ) var _v                : Int?    = null

)

data class LivenessResult (

  @SerializedName("_id"      ) var Id       : String?   = null,
  @SerializedName("response" ) var response : Response? = Response(),
  @SerializedName("sid"      ) var sid      : String?   = null,
  @SerializedName("email"    ) var email    : String?   = null,
  @SerializedName("__v"      ) var _v       : Int?      = null

)

data class Response (

  @SerializedName("AuditImages"    ) var AuditImages    : ArrayList<String> = arrayListOf(),
  @SerializedName("Confidence"     ) var Confidence     : Double?           = null,
  @SerializedName("ReferenceImage" ) var ReferenceImage : ReferenceImage?   = ReferenceImage(),
  @SerializedName("SessionId"      ) var SessionId      : String?           = null,
  @SerializedName("Status"         ) var Status         : String?           = null,
  @SerializedName("metadata"       ) var metadata       : Metadata?         = Metadata()

)

data class Metadata (

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