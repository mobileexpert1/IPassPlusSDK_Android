package com.sdk.ipassplussdk.model.request.valid_api

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("type"  ) var type  : String? = null,
    @SerializedName("image" ) var image : String? = null

)