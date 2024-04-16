package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class OcrPostDataResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)