package com.sdk.ipassplussdk.model.response.aml_manual

data class MatchTypesDetail(
    val aml_types: List<String>,
    val matching_name: String,
    val name_matches: List<NameMatche>,
    val secondary_matches: List<Any>,
    val sources: List<String>
)