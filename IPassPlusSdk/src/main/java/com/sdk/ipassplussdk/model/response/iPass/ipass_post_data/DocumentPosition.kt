package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class DocumentPosition(
    val Angle: Int,
    val Center: Center,
    val Dpi: Int,
    val Height: Int,
    val Inverse: Int,
    val LeftBottom: LeftBottom,
    val LeftTop: LeftTop,
    val ObjArea: Int,
    val ObjIntAngleDev: Int,
    val PerspectiveTr: Int,
    val ResultStatus: Int,
    val RightBottom: RightBottom,
    val RightTop: RightTop,
    val Width: Int,
    val docFormat: Int
)