package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class ListVerifiedFields(
    val Count: Int,
    val pDateFormat: String,
    val pFieldMaps: List<PFieldMap>
)