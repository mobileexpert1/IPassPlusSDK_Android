package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class DetailsRFID(
    val AA: Int,
    val BAC: Int,
    val CA: Int,
    val PA: Int,
    val PACE: Int,
    val TA: Int,
    val overallStatus: Int
)