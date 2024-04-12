package com.sdk.ipassplussdk.model.response.regula.regula_post_data

data class Value(
    val containerType: Int,
    val fieldRect: FieldRect,
    val lightIndex: Int,
    val originalPageIndex: Int,
    val pageIndex: Int,
    val source: String,
    val value: String
)