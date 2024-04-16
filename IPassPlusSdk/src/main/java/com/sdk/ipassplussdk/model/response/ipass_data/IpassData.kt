package com.sdk.ipassplussdk.model.response.ipass_data

import com.google.gson.annotations.SerializedName

data class RegulaData (

  @SerializedName("ChipPage"               ) var ChipPage               : Int?             = null,
  @SerializedName("ContainerList"          ) var ContainerList          : ContainerList?   = ContainerList(),
  @SerializedName("ProcessingFinished"     ) var ProcessingFinished     : Int?             = null,
  @SerializedName("TransactionInfo"        ) var TransactionInfo        : TransactionInfo? = TransactionInfo(),
  @SerializedName("elapsedTime"            ) var elapsedTime            : Int?             = null,
  @SerializedName("hologramTiltType"       ) var hologramTiltType       : Int?             = null,
  @SerializedName("livenessAnimationImage" ) var livenessAnimationImage : Int?             = null,
  @SerializedName("morePagesAvailable"     ) var morePagesAvailable     : Int?             = null,
  @SerializedName("processCommandRes"      ) var processCommandRes      : Int?             = null

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

  @SerializedName("ICAOCode"     ) var ICAOCode     : String?  = null,
  @SerializedName("dCountryName" ) var dCountryName : String?  = null,
  @SerializedName("dDescription" ) var dDescription : String?  = null,
  @SerializedName("dFormat"      ) var dFormat      : Int?     = null,
  @SerializedName("dMRZ"         ) var dMRZ         : Boolean? = null,
  @SerializedName("dType"        ) var dType        : Int?     = null,
  @SerializedName("dYear"        ) var dYear        : String?  = null,
  @SerializedName("isDeprecated" ) var isDeprecated : Boolean? = null

)