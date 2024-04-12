package com.sdk.ipassplussdk.model.response.ceon.GetCeon

data class DataX(
    val account_details: AccountDetails,
    val applied_rules: List<AppliedRule>,
    val breach_details: BreachDetails,
    val deliverable: Boolean,
    val domain_details: DomainDetails,
    val email: String,
    val score: Int
)