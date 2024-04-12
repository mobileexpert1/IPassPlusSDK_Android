package com.sdk.ipassplussdk.model.response.facesimilarity

data class UnmatchedFace(
    val BoundingBox: BoundingBox,
    val Confidence: Double,
    val Landmarks: List<Landmark>,
    val Pose: Pose,
    val Quality: Quality
)