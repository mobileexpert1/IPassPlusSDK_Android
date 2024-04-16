package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class ResultMRZDetector(
    val MRZFormat: Int,
    val MRZRows: List<MRZRow>,
    val MRZRowsNum: Int,
    val boundingQuadrangle: List<Int>
)