package com.sdk.ipassplussdk.model.response.regula.regula_post_data

data class Field(
    val fieldName: String,
    val fieldType: Int,
    val valueList: List<Value>
)