package com.sdk.ipassplussdk.model.response.aml_manual

data class FinalResp(
    val __v: Int,
    val _id: String,
    val code: Int,
    val content: Content,
    val createdAt: String,
    val fuziness_level: String,
    val status: String,
    val user_email: String
)