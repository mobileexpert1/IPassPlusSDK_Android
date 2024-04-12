package com.sdk.ipassplussdk.model.request.regula.regula_get_data_by_email

data class OcrGetDataByEmailRequest(
    val email: String,
    val image1: String,
    val image2: String,
    val sid: String
)