package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class ListVerifiedFields(
    val Count: Int,
    val pDateFormat: String,
    val pFieldMaps: List<PFieldMap>
)