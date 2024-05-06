package com.sdk.ipassplussdk.model.response.document_scanner

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class DocumentScannerResponse (


  @SerializedName("Apistatus"  ) var Apistatus  : Boolean? = null,
  @SerializedName("Apimessage" ) var Apimessage : String?  = null,
  @SerializedName("data"       ) var data       : JsonObject?    = null

)