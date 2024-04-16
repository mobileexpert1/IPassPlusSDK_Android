package com.sdk.ipassplussdk.model.response.ipass.ipass_post_data

data class Status(
    val detailsOptical: DetailsOptical,
    val detailsRFID: DetailsRFID,
    val optical: Int,
    val overallStatus: Int,
    val portrait: Int,
    val rfid: Int,
    val stopList: Int
)