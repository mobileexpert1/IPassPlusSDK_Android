package com.sdk.ipassplussdk.model.response.data_save

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class DataSaveRequest (

  @SerializedName("email"        ) var email        : String?       = null,
  @SerializedName("idvData"    ) var idvData    : JsonObject?    = null,
  @SerializedName("livenessdata" ) var livenessdata : Livenessdata? = Livenessdata(),
  @SerializedName("randomid"     ) var randomid     : String?       = null


)


data class TransactionInfo (

  @SerializedName("ComputerName"  ) var ComputerName  : String? = null,
  @SerializedName("DateTime"      ) var DateTime      : String? = null,
  @SerializedName("SystemInfo"    ) var SystemInfo    : String? = null,
  @SerializedName("TransactionID" ) var TransactionID : String? = null,
  @SerializedName("UserName"      ) var UserName      : String? = null,
  @SerializedName("Version"       ) var Version       : String? = null

)

data class ContainerList (

  @SerializedName("Count" ) var Count : Int?            = null,
  @SerializedName("List"  ) var List  : ArrayList<List> = arrayListOf()

)


data class List (

  @SerializedName("OneCandidate" ) var OneCandidate : OneCandidate? = OneCandidate(),
  @SerializedName("buf_length"   ) var bufLength    : Int?          = null,
  @SerializedName("light"        ) var light        : Int?          = null,
  @SerializedName("list_idx"     ) var listIdx      : Int?          = null,
  @SerializedName("page_idx"     ) var pageIdx      : Int?          = null,
  @SerializedName("result_type"  ) var resultType   : Int?          = null

)

data class OneCandidate (

  @SerializedName("AuthenticityNecessaryLights" ) var AuthenticityNecessaryLights : Int?       = null,
  @SerializedName("CheckAuthenticity"           ) var CheckAuthenticity           : Int?       = null,
  @SerializedName("DocumentName"                ) var DocumentName                : String?    = null,
  @SerializedName("FDSIDList"                   ) var FDSIDList                   : FDSIDList? = FDSIDList(),
  @SerializedName("ID"                          ) var ID                          : Int?       = null,
  @SerializedName("NecessaryLights"             ) var NecessaryLights             : Int?       = null,
  @SerializedName("OVIExp"                      ) var OVIExp                      : Int?       = null,
  @SerializedName("P"                           ) var P                           : Double?    = null,
  @SerializedName("RFID_Presence"               ) var RFIDPresence                : Int?       = null,
  @SerializedName("Rotated180"                  ) var Rotated180                  : Int?       = null,
  @SerializedName("RotationAngle"               ) var RotationAngle               : Int?       = null,
  @SerializedName("UVExp"                       ) var UVExp                       : Int?       = null

)

data class FDSIDList (

  @SerializedName("Count"        ) var Count        : Int?           = null,
  @SerializedName("ICAOCode"     ) var ICAOCode     : String?        = null,
  @SerializedName("List"         ) var List         : ArrayList<Int> = arrayListOf(),
  @SerializedName("dCountryName" ) var dCountryName : String?        = null,
  @SerializedName("dDescription" ) var dDescription : String?        = null,
  @SerializedName("dFormat"      ) var dFormat      : Int?           = null,
  @SerializedName("dMRZ"         ) var dMRZ         : Boolean?       = null,
  @SerializedName("dType"        ) var dType        : Int?           = null,
  @SerializedName("dYear"        ) var dYear        : String?        = null,
  @SerializedName("isDeprecated" ) var isDeprecated : Boolean?       = null

)

data class Livenessdata(
   @SerializedName("_id"      ) var Id       : String?   = null,
   @SerializedName("response" ) var response : Response? = Response(),
   @SerializedName("sid"      ) var sid      : String?   = null,
   @SerializedName("email"    ) var email    : String?   = null,
   @SerializedName("__v"      ) var _v       : Int?      = null
 )

data class Response (

  @SerializedName("\$metadata"      ) var metadata      : metadata?        = metadata(),
  @SerializedName("AuditImages"    ) var AuditImages    : ArrayList<AuditImages> = arrayListOf(),
@SerializedName("Confidence"     ) var Confidence     : Double?           = null,
@SerializedName("ReferenceImage" ) var ReferenceImage : ReferenceImage?   = ReferenceImage(),
@SerializedName("SessionId"      ) var SessionId      : String?           = null,
@SerializedName("Status"         ) var Status         : String?           = null

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


data class metadata (

  @SerializedName("httpStatusCode"  ) var httpStatusCode  : Int?    = null,
  @SerializedName("requestId"       ) var requestId       : String? = null,
  @SerializedName("attempts"        ) var attempts        : Int?    = null,
  @SerializedName("totalRetryDelay" ) var totalRetryDelay : Int?    = null

)

data class AuditImages (

  @SerializedName("BoundingBox" ) var BoundingBox : BoundingBox? = BoundingBox(),
  @SerializedName("Bytes"       ) var Bytes       : String?      = null

)
