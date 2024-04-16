package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class MRZRow(
    val length: Int,
    val maxLength: Int,
    val symbols: List<Symbol>
)