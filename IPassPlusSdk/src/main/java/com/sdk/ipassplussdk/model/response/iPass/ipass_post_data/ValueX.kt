package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class ValueX(
    val containerType: Int,
    val fieldRect: FieldRect,
    val originalSymbols: List<OriginalSymbol>,
    val originalValidity: Int,
    val originalValue: String,
    val pageIndex: Int,
    val probability: Int,
    val source: String,
    val value: String
)