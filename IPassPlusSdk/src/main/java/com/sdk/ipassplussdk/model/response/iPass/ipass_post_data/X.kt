package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class X(
    val featureType: Int,
    val mean: Double,
    val probability: Int,
    val result: Int,
    val std_dev: Double,
    val type: Int
)