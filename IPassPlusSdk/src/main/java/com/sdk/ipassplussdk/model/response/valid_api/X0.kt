package com.sdk.ipassplussdk.model.response.valid_api

data class X0(
    val alert: Boolean,
    val fraud: Fraud,
    val identity: Identity,
    val request_id: String,
    val resource_type: String,
    val timestamp: String,
    val type: String,
    val version: String
)