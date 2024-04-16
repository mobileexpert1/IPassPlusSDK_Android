package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class ResultMRZDetector(
    val MRZFormat: Int,
    val MRZRows: List<MRZRow>,
    val MRZRowsNum: Int,
    val boundingQuadrangle: List<Int>
)