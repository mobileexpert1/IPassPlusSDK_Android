package com.sdk.ipassplussdk.model.response.ceon.GetCeon

data class AppliedRule(
    val id: String,
    val name: String,
    val operation: String,
    val score: Int
)