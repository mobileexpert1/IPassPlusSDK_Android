package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class FieldXX(
    val comparisonList: List<Comparison>,
    val comparisonStatus: Int,
    val fieldName: String,
    val fieldType: Int,
    val lcid: Int,
    val lcidName: String,
    val status: Int,
    val validityList: List<Validity>,
    val validityStatus: Int,
    val value: String,
    val valueList: List<ValueX>
)