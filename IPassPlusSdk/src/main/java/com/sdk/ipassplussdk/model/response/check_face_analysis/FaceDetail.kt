package com.sdk.ipassplussdk.model.response.check_face_analysis

data class FaceDetail(
    val BoundingBox: BoundingBox,
    val Confidence: Double,
    val Landmarks: List<Landmark>,
    val Pose: Pose,
    val Quality: Quality
)