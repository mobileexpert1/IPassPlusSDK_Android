package com.sdk.ipassplussdk.model.response.data_save

import com.google.gson.annotations.SerializedName

data class DataSaveRequest (

  @SerializedName("email"        ) var email        : String? = null,
  @SerializedName("regulaDat"    ) var regulaDat    : String? = null,
  @SerializedName("livenessdata" ) var livenessdata : String? = null,
  @SerializedName("randomid"     ) var randomid     : String? = null

)