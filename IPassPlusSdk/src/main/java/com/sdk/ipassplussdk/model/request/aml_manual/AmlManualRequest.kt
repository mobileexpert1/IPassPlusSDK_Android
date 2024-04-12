package com.sdk.ipassplussdk.model.request.aml_manual

import com.google.gson.annotations.SerializedName

data class AmlManualRequest(
    @SerializedName("email"       ) var email      : String? = null,
    @SerializedName("auth_token"  ) var authToken  : String? = null,
    @SerializedName("entity_name" ) var entityName : String? = null,
    @SerializedName("fuzLevel"    ) var fuzLevel   : String? = null,
    @SerializedName("sid"         ) var sid        : String? = null,
    @SerializedName("custEmail"   ) var custEmail  : String? = null
)