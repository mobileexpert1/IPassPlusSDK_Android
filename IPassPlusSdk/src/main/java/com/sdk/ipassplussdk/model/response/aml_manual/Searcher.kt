package com.sdk.ipassplussdk.model.response.aml_manual

data class Searcher(
    val created_at: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val user_is_active: Boolean
)