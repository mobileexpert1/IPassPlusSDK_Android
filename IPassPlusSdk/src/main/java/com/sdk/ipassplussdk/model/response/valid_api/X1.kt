package com.sdk.ipassplussdk.model.response.valid_api

data class X1(
    val alert: Boolean,
    val fraud: FraudX,
    val identity: IdentityX,
    val request_id: String,
    val resource_type: String,
    val timestamp: String,
    val type: String,
    val version: String
)