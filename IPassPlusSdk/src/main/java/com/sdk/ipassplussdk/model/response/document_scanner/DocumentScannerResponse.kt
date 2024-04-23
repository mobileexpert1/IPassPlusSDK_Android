package com.sdk.ipassplussdk.model.response.document_scanner

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class DocumentScannerResponse (

  @SerializedName("status"  ) var status  : Boolean? = null,
  @SerializedName("message" ) var message : String?  = null,
  @SerializedName("data"    ) var data    : JsonObject?    = null

)
