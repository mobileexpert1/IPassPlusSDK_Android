package com.sdk.ipassplussdk.model.response.ipass.ipass_get_data_by_email

data class TransactionInfo(
    val ComputerName: String,
    val DateTime: String,
    val SystemInfo: String,
    val TransactionID: String,
    val UserName: String,
    val Version: String
)