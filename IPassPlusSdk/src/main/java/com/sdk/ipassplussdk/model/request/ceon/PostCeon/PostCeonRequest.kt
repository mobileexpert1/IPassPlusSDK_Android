package com.sdk.ipassplussdk.model.request.ceon.PostCeon

data class PostCeonRequest(
    val email: String,
    val ipadd: String,
    val phone_number: String,
    val sid: String
)