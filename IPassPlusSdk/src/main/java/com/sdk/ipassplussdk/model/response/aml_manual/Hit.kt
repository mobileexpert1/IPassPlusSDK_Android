package com.sdk.ipassplussdk.model.response.aml_manual

data class Hit(
    val doc: Doc,
    val match_types: List<String>,
    val match_types_details: List<MatchTypesDetail>,
    val score: Double
)