package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class PArrayFieldX(
    val Buf_Length: Int,
    val Buf_Text: String,
    val FieldMask: String,
    val FieldName: String,
    val FieldRect: FieldRect,
    val FieldType: Int,
    val InComparison: Int,
    val Reserved2: Int,
    val Reserved3: Int,
    val StringsCount: Int,
    val StringsResult: List<StringsResult>,
    val Validity: Int,
    val wFieldType: Int,
    val wLCID: Int
)