package com.sdk.ipassplussdk.model.request.valid_api

import com.google.gson.annotations.SerializedName

data class ValidApiRequest(

    @SerializedName("image"         ) var image         : ArrayList<Image> = arrayListOf(),
    @SerializedName("email"         ) var email         : String?          = null,
    @SerializedName("applicationId" ) var applicationId : String?          = null,
    @SerializedName("auth_token"    ) var authToken     : String?          = null,
    @SerializedName("sid"           ) var sid           : String?          = null
)