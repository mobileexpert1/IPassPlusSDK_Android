package com.sdk.ipassplussdk.model.request.facesimilarity

import com.google.gson.annotations.SerializedName

data class FaceSimilarityRequest(
    @SerializedName("sid"        ) var sid       : String? = null,
    @SerializedName("email"      ) var email     : String? = null,
    @SerializedName("auth_token" ) var authToken : String? = null,
    @SerializedName("sourceImageBase64"      ) var sourceImageBase64     : String? = null,
    @SerializedName("targetImageBase64" ) var targetImageBase64 : String? = null,
//    val liveRej: String,
)
