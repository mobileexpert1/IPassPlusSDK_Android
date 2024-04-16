package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class DetailsOptical(
    val docType: Int,
    val expiry: Int,
    val imageQA: Int,
    val mrz: Int,
    val overallStatus: Int,
    val pagesCount: Int,
    val security: Int,
    val text: Int,
    val vds: Int
)