package com.sdk.ipassplussdk.model.response.aml_manual

data class Filters(
    val country_codes: List<Any>,
    val exact_match: Boolean,
    val fuzziness: Int,
    val remove_deceased: Int,
    val types: List<String>
)