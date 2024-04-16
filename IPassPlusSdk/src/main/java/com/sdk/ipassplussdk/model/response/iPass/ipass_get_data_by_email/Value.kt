package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class Value(
    val containerType: Int,
    val fieldRect: FieldRect,
    val lightIndex: Int,
    val originalPageIndex: Int,
    val pageIndex: Int,
    val source: String,
    val value: String
)