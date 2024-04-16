package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class MRZRow(
    val length: Int,
    val maxLength: Int,
    val symbols: List<Symbol>
)