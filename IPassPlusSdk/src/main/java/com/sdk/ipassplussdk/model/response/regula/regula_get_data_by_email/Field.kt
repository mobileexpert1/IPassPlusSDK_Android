package com.sdk.ipassplussdk.model.response.regula.regula_get_data_by_email

data class Field(
    val fieldName: String,
    val fieldType: Int,
    val valueList: List<Value>
)