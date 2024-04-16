package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class MRZTestQuality(
    val CHECK_SUMS: Int,
    val CONTRAST_PRINT: Int,
    val DOC_FORMAT: Int,
    val MRZ_FORMAT: Int,
    val PRINT_POSITION: Int,
    val STAIN_MRZ: Int,
    val SYMBOLS_PARAM: Int,
    val StrCount: Int,
    val Strings: List<String>,
    val TEXTUAL_FILLING: Int
)