package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class Comparison(
    val sourceLeft: String,
    val sourceRight: String,
    val status: Int
)