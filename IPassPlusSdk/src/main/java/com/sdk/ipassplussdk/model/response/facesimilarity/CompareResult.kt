package com.sdk.ipassplussdk.model.response.facesimilarity

data class CompareResult(
    val `$metadata`: Metadata,
    val FaceMatches: List<Any>,
    val SourceImageFace: SourceImageFace,
    val UnmatchedFaces: List<UnmatchedFace>
)