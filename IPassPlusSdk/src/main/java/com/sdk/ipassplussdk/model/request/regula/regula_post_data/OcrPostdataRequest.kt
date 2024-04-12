package com.sdk.ipassplussdk.model.request.regula.regula_post_data

import com.google.gson.annotations.SerializedName

data class OcrPostdataRequest(
    @SerializedName("email"      ) var email     : String? = null,
    @SerializedName("image1"     ) var image1    : String? = null,
    @SerializedName("image2"     ) var image2    : String? = null,
    @SerializedName("sid"        ) var sid       : String? = null,
    @SerializedName("auth_token" ) var authToken : String? = null,
    @SerializedName("custEmail"  ) var custEmail : String? = null,
    @SerializedName("workflow"   ) var workflow  : String? = null
)