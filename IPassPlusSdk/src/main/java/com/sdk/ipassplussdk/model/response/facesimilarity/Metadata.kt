package com.sdk.ipassplussdk.model.response.facesimilarity

data class Metadata(
    val attempts: Int,
    val httpStatusCode: Int,
    val requestId: String,
    val totalRetryDelay: Int
)