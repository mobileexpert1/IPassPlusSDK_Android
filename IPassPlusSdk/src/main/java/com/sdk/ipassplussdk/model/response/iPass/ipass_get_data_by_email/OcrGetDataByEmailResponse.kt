package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class OcrGetDataByEmailResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)