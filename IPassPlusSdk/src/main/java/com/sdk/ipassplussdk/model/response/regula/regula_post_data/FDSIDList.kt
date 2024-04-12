package com.sdk.ipassplussdk.model.response.regula.regula_post_data

data class FDSIDList(
    val ICAOCode: String,
    val dCountryName: String,
    val dDescription: String,
    val dFormat: Int,
    val dMRZ: Boolean,
    val dType: Int,
    val dYear: String,
    val isDeprecated: Boolean
)