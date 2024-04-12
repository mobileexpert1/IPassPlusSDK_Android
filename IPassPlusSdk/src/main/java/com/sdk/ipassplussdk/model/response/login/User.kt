package com.sdk.ipassplussdk.model.response.login

data class User(
    val email: String,
    val token: String,
    val user_id: String
)