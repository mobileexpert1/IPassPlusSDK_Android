package com.sdk.ipassplussdk.model.response.regula.regula_post_data

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