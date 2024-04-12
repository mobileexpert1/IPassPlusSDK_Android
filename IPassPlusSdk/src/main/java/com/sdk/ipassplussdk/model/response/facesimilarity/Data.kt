package com.sdk.ipassplussdk.model.response.facesimilarity

data class Data(
    val compareResult: CompareResult,
    val facePercentage: Int,
    val status: String,
    val success: Boolean
)