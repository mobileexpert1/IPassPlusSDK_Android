package com.sdk.ipassplussdk.model.response.ceon.PostCeon

data class BreachDetails(
    val breaches: List<Breache>,
    val first_breach: String,
    val haveibeenpwned_listed: Boolean,
    val number_of_breaches: Int
)