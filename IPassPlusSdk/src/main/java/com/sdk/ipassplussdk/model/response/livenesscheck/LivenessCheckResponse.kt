package com.sdk.ipassplussdk.model.response.livenesscheck

data class LivenessCheckResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)