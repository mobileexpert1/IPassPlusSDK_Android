package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class Text(
    val availableSourceList: List<AvailableSourceX>,
    val comparisonStatus: Int,
    val dateFormat: String,
    val fieldList: List<FieldXX>,
    val status: Int,
    val validityStatus: Int
)