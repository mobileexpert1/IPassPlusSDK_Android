package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class FaceDetection(
    val Count: Int,
    val CountFalseDetection: Int,
    val Res: List<Re>,
    val Reserved1: Int,
    val Reserved2: Int
)