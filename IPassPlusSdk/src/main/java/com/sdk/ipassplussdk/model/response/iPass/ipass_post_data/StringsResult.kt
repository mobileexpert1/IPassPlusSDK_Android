package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class StringsResult(
    val Reserved: Int,
    val StringResult: List<StringResult>,
    val SymbolsCount: Int
)