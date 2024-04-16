package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

import com.sdk.ipassplussdk.model.response.ipass.ipass_post_data.Rect

data class OriginalSymbol(
    val code: Int,
    val probability: Int,
    val rect: Rect
)