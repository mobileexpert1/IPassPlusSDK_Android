package com.sdk.ipassplussdk.model.response.aml_manual

data class DataX(
    val assignee: Assignee,
    val assignee_id: Int,
    val blacklist_hits: List<Any>,
    val client_ref: Any,
    val created_at: String,
    val filters: Filters,
    val hits: List<Hit>,
    val id: Int,
    val labels: List<Any>,
    val limit: Int,
    val match_status: String,
    val offset: Int,
    val ref: String,
    val risk_level: String,
    val search_term: String,
    val searcher: Searcher,
    val searcher_id: Int,
    val share_url: String,
    val submitted_term: String,
    val tags: List<Any>,
    val total_blacklist_hits: Int,
    val total_hits: Int,
    val total_matches: Int,
    val updated_at: String
)