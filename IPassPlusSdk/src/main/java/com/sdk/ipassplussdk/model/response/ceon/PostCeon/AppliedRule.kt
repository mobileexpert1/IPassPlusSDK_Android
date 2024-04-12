package com.sdk.ipassplussdk.model.response.ceon.PostCeon

data class AppliedRule(
    val id: String,
    val name: String,
    val operation: String,
    val score: Int
)