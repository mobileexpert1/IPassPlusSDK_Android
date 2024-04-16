package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class FieldX(
    val FieldLength: Int,
    val FieldPos: Int,
    val FieldType: Int,
    val TEST_RESULT: Int,
    val ValidCheckSum: Int,
    val reserved: Int
)