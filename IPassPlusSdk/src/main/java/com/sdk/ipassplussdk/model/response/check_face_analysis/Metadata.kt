package com.sdk.ipassplussdk.model.response.check_face_analysis

data class Metadata(
    val attempts: Int,
    val httpStatusCode: Int,
    val requestId: String,
    val totalRetryDelay: Int
)