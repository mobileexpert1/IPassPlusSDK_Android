package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class X(
    val featureType: Int,
    val mean: Double,
    val probability: Int,
    val result: Int,
    val std_dev: Double,
    val type: Int
)