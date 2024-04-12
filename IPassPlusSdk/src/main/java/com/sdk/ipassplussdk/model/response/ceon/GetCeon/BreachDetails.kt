package com.sdk.ipassplussdk.model.response.ceon.GetCeon

data class BreachDetails(
    val breaches: List<Breache>,
    val first_breach: String,
    val haveibeenpwned_listed: Boolean,
    val number_of_breaches: Int
)