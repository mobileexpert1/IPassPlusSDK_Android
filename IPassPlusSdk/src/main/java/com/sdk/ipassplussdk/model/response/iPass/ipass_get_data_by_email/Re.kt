package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class Re(
    val CoincidenceToPhotoArea: Int,
    val FaceRect: FaceRect,
    val FieldRect: FieldRect,
    val GraphFieldNumber: Int,
    val Landmarks: List<Landmark>,
    val LightType: Int,
    val Orientation: Int,
    val Probability: Int
)