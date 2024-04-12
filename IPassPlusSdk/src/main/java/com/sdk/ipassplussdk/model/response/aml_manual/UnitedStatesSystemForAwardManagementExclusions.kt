package com.sdk.ipassplussdk.model.response.aml_manual

data class UnitedStatesSystemForAwardManagementExclusions(
    val aml_types: List<String>,
    val country_codes: List<String>,
    val listing_ended_utc: String,
    val listing_started_utc: String,
    val name: String,
    val url: String
)