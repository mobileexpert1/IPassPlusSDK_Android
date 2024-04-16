package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class PFieldMap(
    val FieldType: Int,
    val Field_MRZ: String,
    val Field_Visual: String,
    val Matrix: List<Int>,
    val wFieldType: Int,
    val wLCID: Int
)