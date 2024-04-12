package com.sdk.ipassplussdk.model.response.regula.regula_post_data

data class MRZRow(
    val length: Int,
    val maxLength: Int,
    val symbols: List<Symbol>
)