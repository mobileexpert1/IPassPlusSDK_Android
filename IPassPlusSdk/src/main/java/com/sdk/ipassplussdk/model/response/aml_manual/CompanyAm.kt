package com.sdk.ipassplussdk.model.response.aml_manual

data class CompanyAm(
    val aml_types: List<String>,
    val country_codes: List<String>,
    val name: String
)