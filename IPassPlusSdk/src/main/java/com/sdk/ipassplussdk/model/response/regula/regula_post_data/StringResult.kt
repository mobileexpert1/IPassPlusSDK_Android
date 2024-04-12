package com.sdk.ipassplussdk.model.response.regula.regula_post_data

data class StringResult(
    val BaseLineBottom: Int,
    val BaseLineTop: Int,
    val CandidatesCount: Int,
    val ListOfCandidates: List<OfCandidates>,
    val Reserved: Int,
    val SymbolRect: SymbolRect
)