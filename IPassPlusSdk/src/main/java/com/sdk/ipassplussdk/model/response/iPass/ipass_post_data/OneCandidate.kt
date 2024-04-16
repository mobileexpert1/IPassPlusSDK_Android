package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class OneCandidate(
    val AuthenticityNecessaryLights: Int,
    val CheckAuthenticity: Int,
    val DocumentName: String,
    val FDSIDList: FDSIDList,
    val ID: Int,
    val NecessaryLights: Int,
    val OVIExp: Int,
    val P: Double,
    val RFID_Presence: Int,
    val Rotated180: Int,
    val RotationAngle: Int,
    val UVExp: Int
)