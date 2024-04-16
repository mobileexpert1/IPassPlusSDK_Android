package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class StringsResult(
    val Reserved: Int,
    val StringResult: List<StringResult>,
    val SymbolsCount: Int
)